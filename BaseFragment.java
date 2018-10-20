package shahzaib.com.traffeee_01;

import android.support.v4.app.Fragment;
import android.view.View;


/**
 * Created by shahzaib on 7/25/2017.
 */

public class BaseFragment extends Fragment {
    private View fragmentView;
    //private BaseExampleFragmentCallbacks mCallbacks;

    public View getFragmentView() {
        return fragmentView;
    }

    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    public View findViewById(int id){
        return getFragmentView().findViewById(id);
    }
   /* protected void attachSearchViewActivityDrawer(FloatingSearchView searchView){
        if(mCallbacks != null){
            mCallbacks.onAttachSearchViewToDrawer(searchView);
        }
    }
    public interface BaseExampleFragmentCallbacks{

        void onAttachSearchViewToDrawer(FloatingSearchView searchView);
    }*/
}
