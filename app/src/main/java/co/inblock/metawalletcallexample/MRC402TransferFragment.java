package co.inblock.metawalletcallexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.inblock.metawalletcallexample.databinding.Mrc010transferFragmentBinding;
import co.inblock.metawalletcallexample.databinding.Mrc402transferFragmentBinding;

public class MRC402TransferFragment extends Fragment {
    private Mrc402transferFragmentBinding binding;

    ActivityResultLauncher<Intent>
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
                        binding.viResult.setText(R.string.success);
                        break;
                        case "9999":
                            binding.viResult.setText(R.string.cancel_by_user);
                            break;
                        default:
                            binding.viResult.setText(R.string.error);
                            break;
                    }
                    binding.viResultCode.setText(code);
                    binding.viResultMessage.setText(bundle.getString("message", ""));
                    binding.viResultTXID.setText(bundle.getString("txid", ""));
                } else if (i == Activity.RESULT_CANCELED) {
                    binding.viResult.setText(R.string.cancel_by_user);
                    binding.viResultCode.setText("");
                    binding.viResultMessage.setText("");
                    binding.viResultTXID.setText("");
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState ) {
        binding = Mrc402transferFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @SuppressLint("QueryPermissionsNeeded")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAction.setOnClickListener(v -> {
            // init
            Uri params = Uri.parse("metawallet://co.inblock");
            Intent intent = new Intent(Intent.ACTION_VIEW, params);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            // set Action
            intent.putExtra("appAction", "mrc402Transfer");

            // Common variables.
            intent.putExtra("appCallback", binding.viAppCallback.getValue());
            intent.putExtra("appReqKey", binding.viAppReqKey.getValue());
            intent.putExtra("appName", binding.viAppName.getValue());
            intent.putExtra("appIcon", binding.viAppIcons.getValue());
            intent.putExtra("appTopic", binding.viTopic.getValue());

            if (binding.rdoMainnet.isChecked()) {
                intent.putExtra("network", "1");
            } else {
                intent.putExtra("network", "5");
            }

            // transfer variables
            intent.putExtra("address", binding.viReceiveAddress.getValue());
            intent.putExtra("amount", binding.viAmount.getValue());
            intent.putExtra("token", binding.viToken.getValue());
            intent.putExtra("tag", binding.viTag.getValue());
            intent.putExtra("memo", binding.viMemo.getValue());
            intent.putExtra("topic", binding.viTopic.getValue());

            binding.viResult.setText("");
            binding.viResultCode.setText("");
            binding.viResultMessage.setText("");
            binding.viResultTXID.setText("");

            try {
                mStartForResult.launch(intent);
            } catch (Exception e) {
                binding.viResult.setText(R.string.metawallet_not_found);
                binding.viResultCode.setText("");
                binding.viResultMessage.setText(e.getLocalizedMessage());
                binding.viResultTXID.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}