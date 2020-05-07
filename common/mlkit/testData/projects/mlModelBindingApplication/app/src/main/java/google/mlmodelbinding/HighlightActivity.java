package google.mlmodelbinding;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import google.mlmodelbinding.ml.MobilenetV105128;
import org.tensorflow.lite.support.image.TensorImage;

public class HighlightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        try {
          MobilenetV105128 model = MobilenetV105128.newInstance(this);
          TensorImage tensorImage = new TensorImage();
          model.process(tensorImage);
        } catch (Exception e) {
          // do nothing
        }
    }
}
