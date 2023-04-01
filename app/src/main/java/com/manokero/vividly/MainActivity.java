package com.manokero.vividly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.manokero.vividly.api.ApiProvider;
import com.manokero.vividly.databinding.ActivityMainBinding;
import com.manokero.vividly.model.ImageModel;
import com.manokero.vividly.model.SearchModel;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private GridLayoutManager layoutManager;
    private Adapter adapter;
    private ArrayList list;
    private AlertDialog dialog;
    private InputMethodManager keyboardManager;

    private int page = 1;
    private final int pageSize = 30;
    private boolean isLoading;
    private boolean isLastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        keyboardManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        list = new ArrayList<ImageModel>();
        adapter = new Adapter(this, list);
        layoutManager = new GridLayoutManager(this, 2);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
        mainBinding.recyclerView.setAdapter(adapter);

        addOnScrollToRV();
        provideEditTextListener();
        renderLoading();
        getData();

        mainBinding.mainInnerToolbar.setNavigationOnClickListener(view -> {
            dialog.show();
            mainBinding.searchEditText.setText("");
            if (keyboardManager.isAcceptingText()) {
                keyboardManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            getData();
        });
    }

    private void getData() {
        isLoading = true;
        ApiProvider.getApiInterface()
                .getImages(page, pageSize)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        if(response.body() != null){
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                        isLoading = false;
                        dialog.dismiss();
                        checkLastPage();
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void searchData(String query) {
        ApiProvider.getApiInterface()
                .getSearchData(query, page, pageSize)
                .enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        dialog.dismiss();
                        list.clear();
                        if(response.body() != null) {
                            list.addAll(response.body().getResults());
                            adapter.notifyDataSetChanged();
                        }
                        isLoading = false;
                        dialog.dismiss();
                        checkLastPage();
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {

                    }
                });
    }

    private void checkLastPage() {
        if(list.size() > 0){
            isLastPage = list.size() < pageSize;
        } else isLastPage = true;
    }

    private void loadMore() {
        int visible = layoutManager.getChildCount();
        int totalItem = layoutManager.getItemCount();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();

        if(!isLoading && !isLastPage){
            if((visible + firstVisible) >= totalItem &&
                    firstVisible >= 0 &&
                    totalItem >= pageSize){
                page++;
                getData();
                dialog.show();
            }
        }
    }

    private void renderLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.custom_progress_bar);
        dialog = builder.create();
        dialog.show();
    }

    private void addOnScrollToRV() {
        mainBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore();
            }
        });
    }

    private void provideEditTextListener() {
        mainBinding.searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {

            if(i == EditorInfo.IME_ACTION_DONE) {
                String query = mainBinding.searchEditText.getText().toString();
                searchData(query);
                if (keyboardManager.isAcceptingText()) {
                    keyboardManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });

        mainBinding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = mainBinding.searchEditText.getText().toString();
                searchData(query);
            }
        });
    }
}