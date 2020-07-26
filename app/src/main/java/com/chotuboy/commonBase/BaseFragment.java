package com.chotuboy.commonBase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chotuboy.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public abstract class BaseFragment extends Fragment {
    protected Toolbar toolbar;
//    protected ProgressDialogFragment progressDialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        progressDialogFragment = ProgressDialogFragment.getInstance();
    }



    public Toolbar initToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        return toolbar;
    }


    public void showMessage(String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showMessage(message);
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    public void showSnackBar(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        } else {
            showMessage(message);
        }
    }

    public void replaceFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack) {
        ((BaseActivity) getActivity()).addReplaceFragment(fragmentName, bundle, container, addToBackStack, true);
    }

    public void addFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack) {
        ((BaseActivity) getActivity()).addReplaceFragment(fragmentName, bundle, container, addToBackStack, false);
    }

    public void replaceChildFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack) {
        addReplaceChildFragment(fragmentName, bundle, container, addToBackStack, true);
    }

    public void addChildFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack) {
        addReplaceChildFragment(fragmentName, bundle, container, addToBackStack, false);
    }


    /**
     * This method used to add/replace fragment on desire container with bundle and back stack status.
     *
     * @param fragmentName
     * @param bundle
     * @param container
     * @param addToBackStack
     * @param isReplace
     */
    public void addReplaceChildFragment(String fragmentName, Bundle bundle, int container, boolean addToBackStack, boolean isReplace) {

        try {
            Fragment fragment = Fragment.instantiate(getActivity(), fragmentName, bundle);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            if (isReplace) {
                transaction.replace(container, fragment, fragmentName);
            } else {
                transaction.add(container, fragment, fragmentName);
            }
            transaction.addToBackStack(addToBackStack == true ? fragment.getClass().getName() : null);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void popUpFragment(String tag) {
        hideSoftKeyboard(getActivity());
        try {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("popUpFragment: ", e.getMessage());
        }
    }

    public void hideSoftKeyboard(Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            // check if no view has focus:
            View v = ((Activity) context).getCurrentFocus();
            if (v == null)
                return;

            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            Log.d("hideSoftKeyboard: ", e.getMessage());
        }
    }



    public abstract void backButtonPressed();

    public abstract void showDialogClick();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftKeyboard(getActivity());
    }

    public String jsonToString(Object jsonObject) {
        return ((BaseActivity) getActivity()).jsonToString(jsonObject);
    }

    public Object stringToJson(String data, Object object) {
        return ((BaseActivity) getActivity()).stringToJson(data, object);
    }





    public void setDate(Calendar calendar, EditText editText, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    public Fragment getCurrentFragment(int id) {
        return getActivity().getSupportFragmentManager().findFragmentById(id);
    }

    public void showAlertDialog(Context context, String message, String text1, String text2) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(message);
        dialog.setPositiveButton(text1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                showDialogClick();
            }
        });
        dialog.setNegativeButton(text2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_account_balance_wallet_24dp));
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.show();
    }


    public boolean checkInternetConnection() {
        return ((BaseActivity) getActivity()).checkInternetConection();
    }

    public boolean checkInternetConnectionWithMessage() {
        boolean internet = checkInternetConnection();
        if (!internet) {
            showSnackBar(getString(R.string.please_check_your_internet_connection));
        }
        return internet;
    }
}
