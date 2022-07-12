package co.inblock.metawalletcallexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import co.inblock.metawalletcallexample.databinding.Mrc402unsellFragmentBinding;

public class MRC402UnSellFragment extends DappCallFragment<Mrc402unsellFragmentBinding> {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = Mrc402unsellFragmentBinding.inflate(inflater, container, false);
        binding.result.getRoot().setVisibility(View.VISIBLE);
        binding.common.getRoot().setVisibility(View.VISIBLE);
        this.bindingResult = binding.result;
        return binding.getRoot();
    }


    @SuppressLint("QueryPermissionsNeeded")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAction.setOnClickListener(v -> {
            Intent intent = getDeepLinkIntent(binding.common, "mrc402Unsell");

            intent.putExtra("id", binding.viDexid.getValue());
            intent.putExtra("from", binding.viSeller.getValue());

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