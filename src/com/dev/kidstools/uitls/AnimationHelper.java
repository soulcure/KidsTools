package com.dev.kidstools.uitls;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {
	public static ScaleAnimation mPosterScaleAnimation;
	public static ScaleAnimation mPosterScaleOutAnimation;
	public static ScaleAnimation mGridItmeScalAnimation;
	public static ScaleAnimation mGridLayoutScalAnimation;
	public static ScaleAnimation mNavTextScalAnimation;
	public static TranslateAnimation mLeftToRight;
	public static TranslateAnimation mRightToLeft;
	public static AlphaAnimation mAlphaInAnimation;
	public static AlphaAnimation mAlphaOutAnimation;
	public static AnimationSet mShowGridLayoutAnimation;
	public static RotateAnimation mRotateAnimation;
	private static int mShortAnimationDuration;

	public static void setupAnimations(Context context) {
		mShortAnimationDuration = context.getResources().getInteger(
				android.R.integer.config_shortAnimTime);
		mPosterScaleAnimation = new ScaleAnimation(1.0f, 1.17f, 1.0f, 1.09f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		mPosterScaleAnimation.setFillAfter(true);
		mPosterScaleAnimation.setDuration(mShortAnimationDuration);

		mPosterScaleOutAnimation = new ScaleAnimation(1.03f, 1.0f, 1.03f, 1.0f,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
				1.0f);
		mPosterScaleOutAnimation.setDuration(mShortAnimationDuration);

		mGridItmeScalAnimation = new ScaleAnimation(1.0f, 1.03f, 1.0f, 1.03f,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
				1.0f);
		mGridItmeScalAnimation.setFillAfter(true);
		mGridItmeScalAnimation.setDuration(mShortAnimationDuration);

		mGridLayoutScalAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mGridLayoutScalAnimation.setDuration(mShortAnimationDuration);

		mNavTextScalAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.3f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		mNavTextScalAnimation.setFillAfter(true);
		mNavTextScalAnimation.setDuration(mShortAnimationDuration);

		mLeftToRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mLeftToRight.setDuration(mShortAnimationDuration);

		mRightToLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mRightToLeft.setDuration(mShortAnimationDuration);

		mAlphaInAnimation = new AlphaAnimation(0.5f, 1.0f);
		mAlphaInAnimation.setDuration(mShortAnimationDuration);

		mAlphaOutAnimation = new AlphaAnimation(1.0f, 0.0f);
		mAlphaOutAnimation.setDuration(1000);

		mShowGridLayoutAnimation = new AnimationSet(false);
		mShowGridLayoutAnimation.addAnimation(mAlphaInAnimation);
		mShowGridLayoutAnimation.addAnimation(mGridLayoutScalAnimation);

		LinearInterpolator lin = new LinearInterpolator();
		mRotateAnimation = new RotateAnimation(0, +360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setDuration(1000);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setInterpolator(lin);
	}
}
