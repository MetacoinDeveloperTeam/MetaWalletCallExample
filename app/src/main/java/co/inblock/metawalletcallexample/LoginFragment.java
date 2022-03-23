package co.inblock.metawalletcallexample;

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

import co.inblock.metawalletcallexample.databinding.LoginFragmentBinding;


public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;

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
                    binding.txtResult.setText(R.string.result_success);
                    binding.txtResultCode.setText(bundle.getString("CODE", ""));
                    binding.txtResultMessage.setText(bundle.getString("MESSAGE", ""));
                    binding.txtResultAddress.setText(bundle.getString("DATA", ""));
                } else if (i == Activity.RESULT_CANCELED) {
                    binding.txtResult.setText(R.string.result_cancel);
                    binding.txtResultCode.setText("");
                    binding.txtResultMessage.setText("");
                    binding.txtResultAddress.setText("");
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(v -> {
            Uri params = Uri.parse("metawallet://co.inblock");
            Intent intent = new Intent(Intent.ACTION_VIEW, params);
            // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("action", "login");
            intent.putExtra("callback", binding.edtCallback.getText().toString());
            intent.putExtra("reqKey", binding.edtReqKey.getText().toString());
            if (binding.rdoMainnet.isChecked()) {
                intent.putExtra("network", "1");
            } else {
                intent.putExtra("network", "5");
            }

            intent.putExtra("callbackType", "intent");
            if (binding.rdodeeplink.isChecked()) {
                intent.putExtra("callbackType", "deeplink");
            } else if (binding.rdoUrl.isChecked()) {
                intent.putExtra("callbackType", "url");
            }

            intent.putExtra("name", binding.edtName.getText().toString());
            intent.putExtra("icons", binding.edtIcons.getText().toString());


            binding.txtResult.setText("");
            binding.txtResultCode.setText("");
            binding.txtResultMessage.setText("");
            binding.txtResultAddress.setText("");

            try {
                mStartForResult.launch(intent);
            } catch (Exception e) {
                binding.txtResult.setText(R.string.metawallet_not_found);
                binding.txtResultCode.setText("");
                binding.txtResultMessage.setText(e.getLocalizedMessage());
                binding.txtResultAddress.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}