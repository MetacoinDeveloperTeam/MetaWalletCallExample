package co.inblock.metawalletcallexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import co.inblock.metawalletcallexample.databinding.Mrc010transferFragmentBinding;

public class MRC010TransferFragment extends DappCallFragment<Mrc010transferFragmentBinding> {


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = Mrc010transferFragmentBinding.inflate(inflater, container, false);
        binding.result.getRoot().setVisibility(View.VISIBLE);
        binding.common.getRoot().setVisibility(View.VISIBLE);
        this.bindingResult = binding.result;
        return binding.getRoot();
    }


    @SuppressLint("QueryPermissionsNeeded")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAction.setOnClickListener(v -> {
            Intent intent = getDeepLinkIntent(binding.common, "send");

            // transfer variables
            intent.putExtra("address", binding.viReceiveAddress.getValue());
            intent.putExtra("amount", binding.viAmount.getValue());
            intent.putExtra("token", binding.viToken.getValue());
            intent.putExtra("tag", binding.viTag.getValue());
            intent.putExtra("memo", binding.viMemo.getValue());

            resultClear();

            try {
                mStartForResult.launch(intent);
            } catch (Exception e) {
                launchError(e);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}