package com.snobwall.nesslet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EditorView extends View
{
	protected Paint _paintObj;
	protected float _fingerSpacing;
	
	protected float _motionstartX=0, _motionstartY=0;
	protected Note _motionstartNote = null;
	
	protected Note[] _ptnNotes;
	protected int _motionstartPtnOffs, _ptnOffs = 0; // in pixels.
	
	protected float _noteHeight = 1;
	protected float _noteWidth = 1;
	
	protected String _logTag = "EditorView";
	
	public EditorView(Context context)
	{
		super(context);
		Log.d(_logTag, "Weirdo constructor 1");
	}
	
	public EditorView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs);
		Log.d(_logTag, "Weirdo constructor 2");
	}

	public EditorView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		System.err.println("EditorView is being added with attributes!");
		
		_paintObj = new Paint(Paint.ANTI_ALIAS_FLAG);
		_paintObj.setTypeface(Typeface.MONOSPACE);
		_paintObj.setTextSize(40);
		_paintObj.setTextAlign(Paint.Align.LEFT);
		_paintObj.setColor(Color.RED);
		
		
		//_ptnNotes = NoteRunner._notes[0];
		_ptnNotes = NoteRunner._testPattern;
		
		
		if (isInEditMode())
		{
			return;
		}
		
		float widths[] = new float[1];
		
		_noteHeight = _paintObj.getFontSpacing();
		_paintObj.getTextWidths("m", widths);
		_noteWidth = widths[0];
			
		Log.d(_logTag, "I think the width is " + _noteWidth);
		
		_fingerSpacing = _paintObj.getFontSpacing() * 1.5f;

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		System.err.println("onTouchEvent: MotionEvent at x=" + me.getX() + ", y=" + me.getY());
		
		if (me.getAction() == MotionEvent.ACTION_DOWN)
		{
			System.err.println("onTouchEvent: Press!");
			_motionstartX = me.getX();
			_motionstartY = me.getY();
			_motionstartPtnOffs = _ptnOffs;
			
			//_motionstartNote = _note;
		}
		
//		if (me.getAction() == MotionEvent.ACTION_MOVE)
//		{
//			_ptnOffs = (int)(_motionstartPtnOffs - (me.getY() - _motionstartY));
//			if (_ptnOffs < 0)
//			{
//				_ptnOffs = 0;
//			}
//			
//			int maxPtnOffs = (int)(_ptnNotes.length * _noteHeight - this.getHeight());
//			if (_ptnOffs > maxPtnOffs)
//			{
//				_ptnOffs = maxPtnOffs;
//			}
//		}
		
		/*
		if (me.getAction() == MotionEvent.ACTION_MOVE)
		{
			_note = (byte)(_motionstartNote + 
						((me.getX() - _motionstartX) / _fingerSpacing));
			
			_note -= 12 * (byte)((me.getY() - _motionstartY) / (_fingerSpacing*2));
		}
		*/
		
		this.invalidate();
		
		return true;
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int mWidth, mHeight;
		
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
		
		mWidth = (int)Math.ceil(3 * _noteWidth);
		if (mWidth < getSuggestedMinimumWidth()) {
			mWidth = getSuggestedMinimumWidth();
		}
		
		int mSpecWidth = View.MeasureSpec.getSize(widthMeasureSpec);

		switch(widthMode) {
		case View.MeasureSpec.EXACTLY:
			mWidth = mSpecWidth;
			break;
		case View.MeasureSpec.AT_MOST:
			if (mWidth > mSpecWidth) {
				mWidth = mSpecWidth;
			}
			break;
		}
		
		// Try to get the right height for the pattern length
		
		mHeight = (int)Math.ceil(_ptnNotes.length * _noteHeight);
		if (mHeight < getSuggestedMinimumHeight()) {
			mHeight = getSuggestedMinimumHeight();
		}
		
		int mSpecHeight = View.MeasureSpec.getSize(heightMeasureSpec);

		switch(heightMode) {
		case View.MeasureSpec.EXACTLY:
			mHeight = mSpecHeight;
			break;
		case View.MeasureSpec.AT_MOST:
			if (mHeight > mSpecHeight) {
				mHeight = mSpecHeight;
			}
			break;
		}
		
		System.err.println("wxh = " + mWidth + "x" + mHeight);
		setMeasuredDimension(mWidth, mHeight);
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
//		System.err.println("onDraw");
		
		int displayable = (int)Math.ceil(this.getHeight() / _noteHeight); // number of displayable notes
		
		int note = (int)(_ptnOffs / _noteHeight); // idx of first displayable note
		float drawStart = _noteHeight - (_ptnOffs % _noteHeight);
//		Log.d(_logTag, "First displayable note is " + note);
//		Log.d(_logTag, "ptnOffs is " + _ptnOffs);
//		Log.d(_logTag, "drawStart is " + drawStart);
		
		for(int i = 0; i < displayable; i++)
		{
			//Log.d(_logTag, Integer.toString(note + i));
			if (i >= _ptnNotes.length) {
				continue;
			}
			
			canvas.drawText(Note.nameOf(_ptnNotes[note+i].noteNum), 0.0f, drawStart + (i * _noteHeight), _paintObj);
			//canvas.drawText(Integer.toString(i), this.getWidth()/2.0f, this.getHeight()/2.0f, _paintObj);
		}
		
		//canvas.drawText(Note.nameOf(_note), this.getWidth()/2.0f, this.getHeight()/2.0f, _paintObj);
		
	}

}
