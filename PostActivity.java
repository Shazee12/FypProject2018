package shahzaib.com.traffeee_01;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import shahzaib.com.traffeee_01.Models.Status_Model;
import shahzaib.com.traffeee_01.Models.latlng;

public class PostActivity extends Fragment {
    private ImageView userimage;
    private ImageButton currentLoc;
    private Button post_btn;
    public LocationManager locationManager;
    public LocationUpdateListener listener;
    public double lat;
    public double log;
    public latlng latLng;
    public String myAddress;
    Geocoder geocoder;
    public String area;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post, container, false);
        currentLoc = view.findViewById(R.id.currentLocation);
        listener = new LocationUpdateListener();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        context = getContext();
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location == null) {
            Toast.makeText(getContext(), "GPS signal not found",
                    Toast.LENGTH_SHORT).show();
        }
        if (location != null) {
            Log.e("locatin", "location--" + location);

            Log.e("latitude at beginning",
                    "@@@@@@@@@@@@@@@" + location.getLatitude());
            listener.onLocationChanged(location);
        }
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);


        final FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();


        if (currentUser != null && currentUser.getPhotoUrl().toString() != null) {
            final ImageView imageView = (ImageView) view.findViewById(R.id.imageViewp);
            TextView textname = (TextView) view.findViewById(R.id.usernamep);
            textname.setText(currentUser.getDisplayName());

            Glide.with(this).load(currentUser.getPhotoUrl().toString()).asBitmap().override(150, 150).centerCrop().into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(PostActivity.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);


                }
            });;


            final EditText etPost = (EditText) view.findViewById(R.id.ePost);
            final Button postBtn = (Button) view.findViewById(R.id.postbtn);

            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String postText = etPost.getText().toString();
                    if (postText.length() <= 0) {
                        etPost.setError("Enter some words");
                    }
                    Log.e("Data Nulls",myAddress+" "+latLng);
                    if (myAddress != null && latLng != null) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                        FirebaseDatabase.getInstance().getReference().child("Posts").push().setValue(new Status_Model(user.getDisplayName(), postText, user.getPhotoUrl().toString(), myAddress, System.currentTimeMillis(), latLng)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isComplete()) {
                                    Toast.makeText(getContext(), "Posted!", Toast.LENGTH_SHORT).show();
                                    etPost.setText("");
                                    getView().setVisibility(View.GONE);




                                } else {
                                    Toast.makeText(getContext(), "Failed Try again later!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                    else {

                        Toast.makeText(getContext(), "Failed Try again !", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }



        return view;
    }
    public class LocationUpdateListener extends Activity implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            log = location.getLongitude();
            latLng = new latlng(lat, log);

            // update your marker here
            Log.e("Curent Location",lat+","+log);

                try {

                    List<Address> addresses = geocoder.getFromLocation(lat, log, 1);

                    if(addresses != null) {
                        Address returnedAddress = addresses.get(0);
                        StringBuilder strReturnedAddress = new StringBuilder();
                        for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        }
                        myAddress = strReturnedAddress.toString();

                    }

                    if (addresses.size() > 0) {
                        area = addresses.get(0).getAddressLine(0);

                        myAddress = area;
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
           /* Volley.newRequestQueue(context).add(new StringRequest("http://maps.googleapis.com/maps/api/geocode/json?sensor=true" + ("&latlng=" + lat + "," + log), new Response.Listener<String>() {
                public void onResponse(String response) {
                    Log.i("offer ", response);
                    try {
                        String formattedAdress = new JSONObject(response).getJSONArray("results").getJSONObject(0).getString("formatted_address");
                        myAddress = formattedAdress;
                        Log.e("user address", formattedAdress);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.i("jsonRequestError", error.toString());
                }
            }));*/


        }







        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

}
