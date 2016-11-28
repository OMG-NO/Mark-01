package com.example.hp.mark_01.Activity;

import android.content.Intent;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.example.hp.mark_01.Application.Constants;
import com.example.hp.mark_01.DB_Project_Type.Dish_evaluate_Type;
import com.example.hp.mark_01.DataBaseUnit.DishDao;
import com.example.hp.mark_01.DataBaseUnit.DishEvaluateDao;
import com.example.hp.mark_01.DataBaseUnit.IngredientDao;
import com.example.hp.mark_01.DataManages.DataManages_Dish;
import com.example.hp.mark_01.Util.BlurBitmap;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.hp.mark_01.Adapter.Adapter_ListAdapter;
import com.example.hp.mark_01.Adapter.Adapter_StepsItem;
import com.example.hp.mark_01.Unit.MyScrollView;
import com.example.hp.mark_01.Util.OnRecyclerItemClickListener;
import com.example.hp.mark_01.DB_Project_Type.DB_Dish_Type;
import com.example.hp.mark_01.R;
import com.example.hp.mark_01.DB_Project_Type.Ingredient_Type;
import com.example.hp.mark_01.Type.Steps_Type;
import com.example.hp.mark_01.Util.OnScrollHeightChangeListener;
import com.example.hp.mark_01.Util.StringUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_Dish extends AppCompatActivity {
    CollapsingToolbarLayout toolbarLayout;
    ImageView dish_albums;

    TextView imtro_text;
    ListView ingredients_list;
    ListView burden_list;
    RecyclerView steps_recycler;

    TextView show_evaluate;
    TextView evaluate_time;
    TextView evaluate_location;

    FloatingActionMenu menuRed;
    FloatingActionButton collection, evaluate, done;

    DB_Dish_Type dbDish;
    List<Steps_Type> steps;
    DishDao dishDao;

    String Imgurl;

    IngredientDao dao;
    List<Ingredient_Type> ingredient_typeslist;

    Dish_evaluate_Type evaluate_type;
    DishEvaluateDao dishEvaluateDao;

    EditText evaluate_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        dao = new IngredientDao(Activity_Dish.this);
        dishDao = new DishDao(Activity_Dish.this);
        initView();


        getPageContent();       //取得页面数据
        setIngredients_list();  //设置原料展示部分
        setSteps_recycler();    //设置步骤展示部分

        setToolbarLayout();
        setFloatbtn();          //设置floatingactionbutton

        showDishEvaluate();
    }

    private void initView() {
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        dish_albums = (ImageView) findViewById(R.id.dish_albums);

        ingredients_list = (ListView) findViewById(R.id.ingredients_list);
        burden_list = (ListView) findViewById(R.id.burden_list);
        steps_recycler = (RecyclerView) findViewById(R.id.steps_recycler);

        show_evaluate = (TextView) findViewById(R.id.show_evaluate);
        evaluate_time = (TextView) findViewById(R.id.evaluate_time);
        evaluate_location = (TextView) findViewById(R.id.evaluate_loaction);

        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
    }

    //设置标题
    public void setToolbarLayout() {
        toolbarLayout.setTitle(dbDish.getTitle());
        try {
            Imgurl = StringUtil.getAlbumsString(new JSONArray(dbDish.getAlbums()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//        new MyhandleBitMap().execute(Imgurl);
        loadImage();
    }

    public void loadImage(){
        Glide.with(this)
                .load(Imgurl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        getBitmapSwatch(resource);
                        Bitmap blurbitmap=BlurBitmap.blur(Activity_Dish.this,resource);
                        dish_albums.setImageBitmap(blurbitmap);
                    }
                });
    }

//    public Bitmap loadImage(String imgurl) {
//        try {
//            Bitmap resource = Picasso.with(Activity_Dish.this)
//                    .load(imgurl)
//                    .get();
//
//            return resource;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    class MyhandleBitMap extends AsyncTask<String, String, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//
//            return loadImage(params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            Bitmap blurbitmap=BlurBitmap.blur(Activity_Dish.this,bitmap);
//            dish_albums.setImageBitmap(blurbitmap);
//        }
//    }

    //将Drawable转换为 BitMap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //取出图片的主色
    private void getBitmapSwatch(Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch != null) {
                    toolbarLayout.setContentScrimColor(swatch.getRgb());
                }
            }
        });
    }


    private void setIngredients_list() {
        ingredient_typeslist = new ArrayList<>();
        List<Ingredient_Type> ingredientlist = StringUtil.getIngredientsList(dbDish.getIngredient());
        List<Ingredient_Type> burdenlist = StringUtil.getIngredientsList(dbDish.getBurden());
        ingredient_typeslist.addAll(ingredientlist);
        ingredient_typeslist.addAll(burdenlist);

        BaseAdapter ingredient_adapter = new Adapter_ListAdapter(ingredientlist, Activity_Dish.this);
        BaseAdapter burdenlist_adapter = new Adapter_ListAdapter(burdenlist, Activity_Dish.this);

        ingredients_list.setAdapter(ingredient_adapter);
        burden_list.setAdapter(burdenlist_adapter);
    }

    private void setSteps_recycler() {
        //设置 !!!!!!!!!!!!!!!
        steps = new ArrayList<>();
        try {
            JSONArray stepJson = new JSONArray(dbDish.getSteps());
            Log.i("数据", "stepJson 数据    " + stepJson);
            steps.addAll(StringUtil.getStepsList(stepJson));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Dish.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Adapter_StepsItem adapter_steps = new Adapter_StepsItem(steps, Activity_Dish.this);

        steps_recycler.setLayoutManager(layoutManager);
        steps_recycler.setAdapter(adapter_steps);

        adapter_steps.setItemClickListener(new myRecycleItemClickListener());
    }

    //取得 Activity_Display 页面传来的 db_dish数据
    private void getPageContent() {
        Bundle bundle = getIntent().getBundleExtra("dishdata");
        dbDish = bundle.getParcelable("dishdata");
        Log.i("接收传来的数据", "dbDish 是否长藏  " + dbDish.isCollect());

        //获取菜品的评论数据
        DataManages_Dish.Evaluate.getAllEvaluate();
    }

    //设置floatingactionbutton
    private void setFloatbtn() {
        collection = (FloatingActionButton) findViewById(R.id.collection);
        evaluate = (FloatingActionButton) findViewById(R.id.evaluate);
        done = (FloatingActionButton) findViewById(R.id.done);

        menuRed.setClosedOnTouchOutside(true);

        collection.setOnClickListener(new fabClickListener());
        evaluate.setOnClickListener(new fabClickListener());
        done.setOnClickListener(new fabClickListener());
    }

    private class fabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.collection:
                    boolean iscollect = dbDish.isCollect();
                    Log.i("fab点击事件", "收藏   iscollect   " + iscollect);
                    dbDish.setCollect(!iscollect);

                    dishDao.updataDish(dbDish);
                    DataManages_Dish.updataSoftData();
                    Log.i("收藏", "是否收藏" + dbDish.isCollect());
                    Toast.makeText(Activity_Dish.this, "已将菜品添加至收藏列表", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.evaluate:
                    Log.i("fab点击事件", "评论");
                    menuRed.hideMenu(true);
                    initPopup();

                    break;
                case R.id.done:
                    Log.i("fab点击事件", "采购单");
                    dao.addIngredientToTable(ingredient_typeslist);
                    Toast.makeText(Activity_Dish.this, "已将所需配料添加至采购单", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public void initPopup() {
        PopupWindow popup = new PopupWindow(Activity_Dish.this);
        View popup_view = LayoutInflater.from(this).inflate(R.layout.popup_view, null);
        View parent_view = LayoutInflater.from(this).inflate(R.layout.activity_dish, null);
        popup.setContentView(popup_view);

        popup.setFocusable(true);
        int color = getResources().getColor(R.color.popup_background);
        popup.setBackgroundDrawable(new ColorDrawable(color));
        popup.setOutsideTouchable(true);

        popup.setAnimationStyle(R.style.popup_anim);

        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setEvaluate(popup_view);

        popup.showAtLocation(parent_view, Gravity.TOP, 0, 0);

        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.7f;
        getWindow().setAttributes(layoutParams);


        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                menuRed.showMenu(true);
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
                saveEvaluate();
                Toast.makeText(Activity_Dish.this, "笔记已保存", Toast.LENGTH_SHORT).show();
                showDishEvaluate();
            }
        });
    }

    public void setEvaluate(View view) {
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView tags = (TextView) view.findViewById(R.id.tags);
        TextView imtro = (TextView) view.findViewById(R.id.imtro);
        ImageView albums = (ImageView) view.findViewById(R.id.albums);
        ImageView iscollect = (ImageView) view.findViewById(R.id.iscollect);

        TextView creat_time = (TextView) view.findViewById(R.id.creat_time);
        TextView location = (TextView) view.findViewById(R.id.location);
        evaluate_text = (EditText) view.findViewById(R.id.evaluate_text);

        title.setText(dbDish.getTitle());
        tags.setText(dbDish.getTags());
        imtro.setText(dbDish.getImtro());
        Glide.with(this)
                .load(Imgurl)
                .into(albums);
        if (dbDish.isCollect()) {
            iscollect.setImageResource(R.drawable.collected);
        }

        Calendar calendar = Calendar.getInstance();
        StringBuilder time = new StringBuilder();
        time.append(calendar.get(Calendar.YEAR)).append("-")
                .append(calendar.get(Calendar.MONTH) + 1).append("-")
                .append(calendar.get(calendar.DAY_OF_MONTH)).append(" ")
                .append(calendar.get(calendar.HOUR_OF_DAY)).append(":")
                .append(calendar.get(Calendar.MINUTE));

        creat_time.setText(time.toString());
        if (Constants.LOCATION_POSITION_CITY != null) {
            location.setText(Constants.LOCATION_POSITION_CITY);
        }

        //创建新的评论
        evaluate_type = new Dish_evaluate_Type();
        evaluate_type.setDishID(dbDish.getID());
        evaluate_type.setTime(time.toString());
        if (Constants.LOCATION_POSITION_CITY != null) {
            evaluate_type.setLacation(Constants.LOCATION_POSITION_CITY);
        }
    }

    public void saveEvaluate() {
        String text = evaluate_text.getText().toString();
        evaluate_type.setEvaluate(text);

        if (!text.equals("")) {
            dishEvaluateDao = new DishEvaluateDao(this);
            dishEvaluateDao.addEvaluateToTable(evaluate_type);
            DataManages_Dish.Evaluate.updateEvaluate(evaluate_type);

            Toast.makeText(this, "保存评论数据", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDishEvaluate() {
        Dish_evaluate_Type type = DataManages_Dish.Evaluate.getEvaluateById(dbDish.getID());
        if (type != null) {
            Log.i("评论数据", "评论  " + type.getEvaluate());
            evaluate_type = type;
            show_evaluate.setText(evaluate_type.getEvaluate());
            evaluate_time.setText(evaluate_type.getTime());
            evaluate_location.setText(evaluate_type.getLacation());
            evaluate_type.setLacation(evaluate_type.getLacation());
        }
    }

    class myRecycleItemClickListener implements OnRecyclerItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(Activity_Dish.this, steps.get(position).getStep(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Activity_Dish.this, Activity_Step.class);
            Bundle bundle = getIntent().getBundleExtra("dishdata");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}
