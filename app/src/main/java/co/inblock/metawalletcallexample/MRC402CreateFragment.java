package co.inblock.metawalletcallexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import co.inblock.metawalletcallexample.databinding.Mrc402createFragmentBinding;

public class MRC402CreateFragment extends DappCallFragment<Mrc402createFragmentBinding> {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = Mrc402createFragmentBinding.inflate(inflater, container, false);
        binding.result.getRoot().setVisibility(View.VISIBLE);
        binding.common.getRoot().setVisibility(View.VISIBLE);
        this.bindingResult = binding.result;
        return binding.getRoot();
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAction.setOnClickListener(v -> {
            Intent intent = getDeepLinkIntent(binding.common, "mrc402Create");

            intent.putExtra("name", binding.viName.getValue());
            intent.putExtra("creator", binding.viCreator.getValue());
            intent.putExtra("creatorcommission", binding.viCreatorcommission.getValue());
            intent.putExtra("totalSupply", binding.viTotalSupply.getValue());
            intent.putExtra("decimal", binding.viDecimal.getValue());

            intent.putExtra("url", binding.viUrl.getValue());
            intent.putExtra("imageUrl", binding.viImageUrl.getValue());
            intent.putExtra("shareholder", binding.viShareholder.getValue());
            intent.putExtra("initialreserve", binding.viInitialReserve.getValue());
            intent.putExtra("expiredate", binding.viExpiredate.getValue());
            intent.putExtra("data", binding.viData.getValue());
            intent.putExtra("information", binding.viInformation.getValue());
            intent.putExtra("socialmedia", binding.viSocialmedia.getValue());

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