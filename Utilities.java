

package com.adkdevelopment.rssreader.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ViewAnimationUtils;

import com.adkdevelopment.rssreader.R;
import com.adkdevelopment.rssreader.data.local.NewsRealm;
import com.adkdevelopment.rssreader.data.remote.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utilities {
    private static final String TAG = Utilities.class.getSimpleName();


    public static long convertDate(String unformattedDate) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        Date date = new Date();

        try {
            date = simpleDateFormat.parse(unformattedDate);
        } catch (ParseException e) {
            Log.e(TAG, "e:" + e);
        }

        return date.getTime();
    }


    public static String getFormattedDate(long unformattedDate) {

        Date date = new Date(unformattedDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a, MMM d, yyyy", Locale.getDefault());

        return simpleDateFormat.format(date);
    }


    public static NewsRealm convertNews(Item item) {
        NewsRealm newsItem = new NewsRealm();
        newsItem.setDescription(item.getDescription());
        newsItem.setPubDate(convertDate(item.getPubDate()));
        newsItem.setLink(item.getLink());
        newsItem.setTitle(item.getTitle());
        if (item.getThumbnail() != null) {
            newsItem.setThumbnail(item.getThumbnail().getUrl());
            newsItem.setWidth(Integer.parseInt(item.getThumbnail().getWidth()));
            newsItem.setHeight(Integer.parseInt(item.getThumbnail().getHeight()));
        }
        return newsItem;
    }


    public static String getRelativeDate(Long millis) {
        Date date = new Date(millis);
        return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
    }

    private static int sBlueColor;
    private static int sWhiteColor;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animationCard(RecyclerView.ViewHolder viewHolder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (sBlueColor == 0) {
                sBlueColor = ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorPrimary);
            }
            if (sWhiteColor == 0) {
                sWhiteColor = ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.white);
            }

            int finalRadius = (int) Math.hypot(viewHolder.itemView.getWidth() / 2, viewHolder.itemView.getHeight() / 2);

            Animator anim = ViewAnimationUtils.createCircularReveal(viewHolder.itemView,
                    viewHolder.itemView.getWidth() / 2,
                    viewHolder.itemView.getHeight() / 2, 0, finalRadius);

            viewHolder.itemView.setBackgroundColor(sBlueColor);
            anim.start();
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    viewHolder.itemView.setBackgroundColor(sWhiteColor);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }


    public static boolean isOnline(Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }


    public static int[] getPalette(BitmapDrawable drawable) {

        int[] colors = new int[]{0, 0, 0};
        if (drawable != null) {
            Palette palette = Palette.from(drawable.getBitmap()).generate();

            colors[0] = palette.getLightVibrantColor(0);
            if (colors[0] == 0) {
                colors[0] = palette.getLightMutedColor(0);
            }

            colors[1] = palette.getVibrantColor(0);
            if (colors[1] == 0) {
                colors[1] = palette.getMutedColor(0);
            }

            colors[2] = palette.getDarkVibrantColor(0);
            if (colors[2] == 0) {
                colors[2] = palette.getDarkMutedColor(0);
            }
        }

        return colors;
    }
}
