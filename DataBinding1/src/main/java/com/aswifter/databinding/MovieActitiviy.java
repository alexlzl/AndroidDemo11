package com.aswifter.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.afollestad.materialdialogs.MaterialDialog;
import com.aswifter.databinding.databinding.ActivityMovieBinding;
import com.aswifter.databinding.databinding.MovieItemBinding;
import com.aswifter.databinding.model.Movie;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Chenyc on 15/7/11.
 */
public class MovieActitiviy extends AppCompatActivity {

    private ActivityMovieBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        binding.toolbar.setTitle("电影");
        setSupportActionBar(binding.toolbar);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieActitiviy.this, "test", Toast.LENGTH_LONG).show();
                doSearch(getString(R.string.love));
//                new MaterialDialog.Builder(MovieActitiviy.this)
//                        .title(R.string.search)
//                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                if (!TextUtils.isEmpty(input)) {
//                                    doSearch(input.toString());
//                                }
//                            }
//                        }).show();
            }
        });

        doSearch(getString(R.string.default_search_tag));

    }

    private void doSearch(String keyword) {
        binding.progressBar.setVisibility(View.VISIBLE);
        Movie.searchMovies(keyword, new Movie.IMovieResponse<List<Movie>>() {
            @Override
            public void onData(List<Movie> books) {
                MyAdapter mAdapter = new MyAdapter(MovieActitiviy.this, books);
                binding.recyclerView.setAdapter(mAdapter);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.BindingHolder> {
        //private final int mBackground;
        private List<Movie> mMovies;

        private final TypedValue mTypedValue = new TypedValue();


        public class BindingHolder extends RecyclerView.ViewHolder {
            private MovieItemBinding binding;

            public BindingHolder(View v) {
                super(v);
            }

            public MovieItemBinding getBinding() {
                return binding;
            }

            public void setBinding(MovieItemBinding binding) {
                this.binding = binding;
            }
        }

        public MyAdapter(Context context, List<Movie> movies) {
            mMovies = movies;
        }

        @Override
        public MyAdapter.BindingHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            MovieItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.movie_item,
                    parent,
                    false);
            BindingHolder holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }

        /**
         * 说明在使用ListView、GridView、RecyclerView的时候，由于绑定的类不能确定，比如RecyclerView只有在onBindViewHolder()方法中才能确定绑定的Item，所以我们只有在该办法中动态得到Binding Class（ViewModel）、动态绑定数据。
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            Movie movie = mMovies.get(position);
            Glide.with(MovieActitiviy.this)
                    .load(movie.getImages().getMedium())
                    .fitCenter()
                    .into(holder.binding.ivMovie);
            holder.binding.setVariable(com.aswifter.databinding.BR.movie, movie);
            holder.binding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }
    }
}
