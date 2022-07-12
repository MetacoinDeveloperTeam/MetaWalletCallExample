package co.inblock.metawalletcallexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class viewItem extends ConstraintLayout {
    Drawable edtBackground = null;
    TextView txtLabel;
    TextView edtValue;
    TextView txtRequire;
    Context context;
    boolean isReadOnly = false;
    private String Label, Value;

    public viewItem(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public viewItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public viewItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View v = inflate(context, R.layout.view_item, this);

        this.context = context;
        txtLabel = v.findViewById(R.id.txtLabel);
        edtValue = v.findViewById(R.id.edtValue);
        txtRequire = v.findViewById(R.id.txtRequire);

        edtValue.setOnClickListener(v1 -> {
            if (isReadOnly) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = inflate(context, R.layout.dialog_text_input, null);
            final TextInputEditText input = dialogView.findViewById(R.id.input);
            final TextInputLayout input_wrap = dialogView.findViewById(R.id.input_wrap);
            input_wrap.setHint(txtLabel.getText());
            builder.setView(dialogView);
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                dialog.dismiss();
                if (input != null && input.getText() != null) {
                    edtValue.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
            input.setText(edtValue.getText());
            builder.show();
        });

        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewItemAttrs, defStyleAttr, 0);
        setLabel(typedArray.getString(R.styleable.ViewItemAttrs_Item_label));
        setText(typedArray.getString(R.styleable.ViewItemAttrs_Item_value));
        setRequire(typedArray.getBoolean(R.styleable.ViewItemAttrs_Item_require, false));
        setReadOnly(typedArray.getBoolean(R.styleable.ViewItemAttrs_Item_readonly, false));
        typedArray.recycle();
    }

    public String getLabel() {
        return Label;
    }

    public void setRequire(boolean isRequire) {
        if(isRequire) {
            txtRequire.setVisibility(View.VISIBLE);
        } else {
            txtRequire.setVisibility(View.GONE);
        }
        invalidate();
        requestLayout();
    }

    public void setLabel(String label) {
        txtLabel.setText(label);
        Label = label;
        invalidate();
        requestLayout();
    }

    public void setLabel(@StringRes int ResID) {
        txtLabel.setText(ResID);
        Label = context.getString(ResID);
        invalidate();
        requestLayout();
    }

    public void setText(String value) {
        edtValue.setText(value);
        Value = value;
        invalidate();
        requestLayout();
    }

    public void setText(@StringRes int ResID) {
        edtValue.setText(ResID);
        Value = context.getString(ResID);
        invalidate();
        requestLayout();
    }

    public String getValue() {
        return edtValue.getText().toString();
    }

    public void setValue(String value) {
        Value = value;
        edtValue.setText(Value);
        invalidate();
        requestLayout();
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
        if (isReadOnly) {
            if (edtBackground == null) {
                edtBackground = edtValue.getBackground();
            }
            edtValue.setBackgroundResource(R.drawable.app_btn_border_readonly);
        } else {
            edtValue.setBackgroundResource(R.drawable.app_btn_border);
            //edtValue.setBackground(edtBackground);
        }
        invalidate();
        requestLayout();
    }
}