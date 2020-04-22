package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ItemView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private Bitmap bmpe;
    private Bitmap bmped;
    private SurfaceHolder holder;
    private static ArrayList<Item> mItems;
    private Rect mFieldDim = new Rect( );
    private ArrayList<PointF> glass;
    private static ArrayList<Item> mInventory;



    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        loadImages();

    }





    public void surfaceCreated(SurfaceHolder holder) {
        holder = holder;
        Canvas c = holder.lockCanvas( );
        mFieldDim.set(0, 0, c.getWidth( ), c.getHeight( ));
        if (c != null)
            holder.unlockCanvasAndPost(c);


        for (int i = 0; i < mItems.size( ); i++) {
            mItems.get(i).x = (int) (Math.random( ) * (float) mFieldDim.width( ));
            mItems.get(i).y = (int) (Math.random( ) * (float) mFieldDim.height( ));
        }
        invalidate( );
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {


    }

    public void loadImages() {
        bmped = BitmapFactory.decodeResource(getResources( ), R.drawable.iteme);

    }

    public void init() {
        getHolder( ).addCallback(this);
        setOnTouchListener(this);
        mItems = loadItems( );
        glass = new ArrayList<PointF>( );
        bmpe = BitmapFactory.decodeResource(getResources( ), R.drawable.area);
        bmped = BitmapFactory.decodeResource(getResources( ), R.drawable.iteme);
        mInventory = new ArrayList<Item>( );
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        if(motionEvent.getAction()== motionEvent.ACTION_DOWN){
            PointF touch = new PointF(motionEvent.getX(), motionEvent.getY());
            glass.add(touch);
            int radius= Math.max(bmped.getHeight(), bmped.getWidth())/2;
            for (int i =  mItems.size( ) -1; i >= 0; i--) {

                Item item =mItems.get(i);
                int dx = (int) (item.x -  touch.x);
                int dy = (int) (item.y -  touch.y);
                if(dx*dx + dy*dy< radius *radius ){
                    mItems.remove(i);
                    mInventory.add(item);
                    Toast toast = Toast.makeText(this.getContext(), item.name + "collected",  Toast.LENGTH_LONG);
                    toast.show();

                }
            }
            invalidate();
        }

        return true;}


    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);
        if(bmpe != null){
            canvas.drawBitmap(bmpe,  0, 0, new Paint());
            final Rect rect = new Rect(0, 0, bmpe.getWidth(), bmpe.getHeight());
        }

        if(bmped != null){
            for(PointF shatter : glass ){
                canvas.drawBitmap(bmped, (int) (shatter.x- bmped.getWidth()), (int) (shatter.y- bmped.getHeight()) , new Paint());
                final Rect rect = new Rect(0, 0, bmped.getWidth(), bmped.getHeight());
                final RectF rext = new RectF(0, 0,(int) (shatter.x+ bmped.getWidth()), (int) (shatter.y+ bmped.getHeight()));



            }

        }

    }




    public ArrayList<Item> loadItems() {


        InputStream input = getResources( ).openRawResource(R.raw.itemo);
        BufferedReader reader = null;
        ArrayList<Item> items = new ArrayList<>();
        String itemName;
        String[] tokens;

        try {
            reader = new BufferedReader(new InputStreamReader(input));
            while ((itemName = reader.readLine( )) != null) {
                items.add(new Item(itemName));
            }
        } catch (Exception e) {
            Log.e("Main12Activity", "Reading list of Items failed!", e);
        } finally {
            try {
                if (reader != null) reader.close( );
            } catch (Exception e) {
                Log.e("Main12Activity", "Error closing file reader.", e);
            }
        }


        return items;
    }


    public static ArrayList<Item> getInventory() {

        return mInventory;
    }



}

