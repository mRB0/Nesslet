package com.snobwall.nesslet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EditorView extends View {
	
	protected Paint _paintObj;
	protected float _fingerSpacing;
	
	protected float _motionstartX=0, _motionstartY=0;
	protected byte _motionstartNote = 1;
	
//	public EditorView(Context context)
//	{
//		super(context);
//		System.err.println("EditorView is being added!");
//	}
	
	public EditorView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		System.err.println("EditorView is being added with attributes!");
		
		_paintObj = new Paint(Paint.ANTI_ALIAS_FLAG);
		_paintObj.setTypeface(Typeface.MONOSPACE);
		_paintObj.setTextSize(16);
		_paintObj.setTextAlign(Paint.Align.CENTER);
		_paintObj.setColor(Color.BLACK);
		
		System.err.println("Font spacing is " + _paintObj.getFontSpacing());
		
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
			_motionstartNote = _note;
		}
		
		if (me.getAction() == MotionEvent.ACTION_MOVE)
		{
			_note = (byte)(_motionstartNote + 
						((me.getX() - _motionstartX) / _fingerSpacing));
			
			_note -= 12 * (byte)((me.getY() - _motionstartY) / (_fingerSpacing*2));
		}
		
		
		this.invalidate();
		
		return true;
	}
	
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//	{
//		System.err.println("Measured width is " + MeasureSpec.getSize(widthMeasureSpec));
//		
//		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 
//				MeasureSpec.getSize(heightMeasureSpec));
//	}
	
	protected byte _note = 56;
	
	@Override
	public void onDraw(Canvas canvas)
	{
		System.err.println("onDraw");
		
		Paint pObj = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		pObj.setColor(Color.GREEN);
		canvas.drawCircle(this.getWidth()/2.0f, this.getHeight()/2.0f, this.getWidth()/3.0f, pObj);
		
		canvas.drawText(Note.nameOf(_note), this.getWidth()/2.0f, this.getHeight()/2.0f, _paintObj);
		
	}
}
