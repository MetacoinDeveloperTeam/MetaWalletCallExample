package co.inblock.metawalletcallexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import co.inblock.metawalletcallexample.databinding.Mrc402sellFragmentBinding;

public class MRC402SellFragment extends DappCallFragment<Mrc402sellFragmentBinding> {


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = Mrc402sellFragmentBinding.inflate(inflater, container, false);
        binding.result.getRoot().setVisibility(View.VISIBLE);
        binding.common.getRoot().setVisibility(View.VISIBLE);
        this.bindingResult = binding.result;
        return binding.getRoot();
    }


    @SuppressLint("QueryPermissionsNeeded")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAction.setOnClickListener(v -> {
            Intent intent = getDeepLinkIntent(binding.common, "mrc402Sell");

            intent.putExtra("id", binding.viMRC402ID.getValue());
            intent.putExtra("amount", binding.viAmount.getValue());
            intent.putExtra("token", binding.viToken.getValue());
            intent.putExtra("price", binding.viPrice.getValue());
            intent.putExtra("from", binding.viSeller.getValue());
            intent.putExtra("platform_address", binding.viPlatformAddress.getValue());
            intent.putExtra("platform_commission", binding.viPlatformCommission.getValue());
            intent.putExtra("platform_name", binding.viPlatformName.getValue());
            intent.putExtra("platform_url", binding.viPlatFormURL.getValue());

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