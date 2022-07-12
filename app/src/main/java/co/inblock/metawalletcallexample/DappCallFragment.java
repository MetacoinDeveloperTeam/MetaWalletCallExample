package co.inblock.metawalletcallexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import co.inblock.metawalletcallexample.databinding.CommonAppinfoFragmentBinding;
import co.inblock.metawalletcallexample.databinding.CommonResultFragmentBinding;

public class DappCallFragment<H> extends Fragment {
    protected CommonResultFragmentBinding bindingResult = null;
    protected H binding;
    protected ActivityResultLauncher<Intent>
            mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                int i = result.getResultCode();
                if (i == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent == null) {
                        return;
                    }
                    Bundle bundle = intent.getExtras();
                    String code = bundle.getString("code", "");
                    switch (code) {
                        case "0000":
                            bindingResult.viResult.setText(R.string.success);
                            break;
                        case "9999":
                            bindingResult.viResult.setText(R.string.cancel_by_user);
                            break;
                        default:
                            bindingResult.viResult.setText(R.string.error);
                            break;
                    }
                    bindingResult.viResultCode.setText(code);
                    bindingResult.viResultMessage.setText(bundle.getString("message", ""));
                    bindingResult.viResultTXID.setText(bundle.getString("txid", ""));
                } else if (i == Activity.RESULT_CANCELED) {
                    bindingResult.viResult.setText(R.string.cancel_by_user);
                    bindingResult.viResultCode.setText("");
                    bindingResult.viResultMessage.setText("");
                    bindingResult.viResultTXID.setText("");
                }
            });

    protected Intent getDeepLinkIntent(CommonAppinfoFragmentBinding appinfo, String action) {
        Uri params = Uri.parse("metawallet://co.inblock");
        Intent intent = new Intent(Intent.ACTION_VIEW, params);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // set Action
        intent.putExtra("appAction", action);

        // Common variables.

        intent.putExtra("appCallback", appinfo.viAppCallback.getValue());
        intent.putExtra("appReqKey", appinfo.viAppReqKey.getValue());
        intent.putExtra("appName", appinfo.viAppName.getValue());
        intent.putExtra("appIcon", appinfo.viAppIcons.getValue());

        if (appinfo.rdoMainnet.isChecked()) {
            intent.putExtra("network", "1");
        } else {
            intent.putExtra("network", "5");
        }
        return intent;
    }

    protected void resultClear() {
        bindingResult.viResult.setText("");
        bindingResult.viResultCode.setText("");
        bindingResult.viResultMessage.setText("");
        bindingResult.viResultTXID.setText("");
    }

    protected void launchError(Exception e) {
        bindingResult.viResult.setText(R.string.metawallet_not_found);
        bindingResult.viResultCode.setText("");
        bindingResult.viResultMessage.setText(e.getLocalizedMessage());
        bindingResult.viResultTXID.setText("");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}