package co.inblock.metawalletcallexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import co.inblock.metawalletcallexample.databinding.TransferFragmentBinding;


public class TransferFragment extends Fragment {
    private TransferFragmentBinding binding;

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
                    binding.txtResult.setText(R.string.success);
                    binding.txtResultCode.setText(bundle.getString("CODE", ""));
                    binding.txtResultMessage.setText(bundle.getString("MESSAGE", ""));
                    binding.txtResultTXID.setText(bundle.getString("TXID", ""));
                } else if (i == Activity.RESULT_CANCELED) {
                    binding.txtResult.setText(R.string.cancel_by_user);
                    binding.txtResultCode.setText("");
                    binding.txtResultMessage.setText("");
                    binding.txtResultTXID.setText("");
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
        binding = TransferFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @SuppressLint("QueryPermissionsNeeded")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst2.setOnClickListener(v -> {
            Uri params = Uri.parse("metawallet://co.inblock");
            Intent intent = new Intent(Intent.ACTION_VIEW, params);

            // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("action", "send");
            intent.putExtra("address", binding.edtReceiveAddress.getText().toString());
            intent.putExtra("amount", binding.edtAmount.getText().toString());
            if (binding.rdoMainnet.isChecked()) {
                intent.putExtra("network", "1");
            } else {
                intent.putExtra("network", "5");
            }
            intent.putExtra("token", binding.edtToken.getText().toString());

            // Option
            intent.putExtra("tag", binding.edtTag.getText().toString());
            intent.putExtra("memo", binding.edtMemo.getText().toString());
            intent.putExtra("topic", binding.edtTopic.getText().toString());

            binding.txtResult.setText("");
            binding.txtResultCode.setText("");
            binding.txtResultMessage.setText("");
            binding.txtResultTXID.setText("");

            try {
                mStartForResult.launch(intent);
            } catch (Exception e) {
                binding.txtResult.setText(R.string.metawallet_not_found);
                binding.txtResultCode.setText("");
                binding.txtResultMessage.setText(e.getLocalizedMessage());
                binding.txtResultTXID.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}