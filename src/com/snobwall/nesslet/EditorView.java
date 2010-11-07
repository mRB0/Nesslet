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
	
	protected String _logTag = "EditorView";
	
	public EditorView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		System.err.println("EditorView is being added with attributes!");
		
		_paintObj = new Paint(Paint.ANTI_ALIAS_FLAG);
		_paintObj.setTypeface(Typeface.MONOSPACE);
		_paintObj.setTextSize(40);
		_paintObj.setTextAlign(Paint.Align.LEFT);
		_paintObj.setColor(Color.RED);
		
		
		_ptnNotes = NoteRunner._notes[0];
		
		
		if (isInEditMode())
		{
			return;
		}
		
		_noteHeight = _paintObj.getFontSpacing();
		
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
		
		if (me.getAction() == MotionEvent.ACTION_MOVE)
		{
			_ptnOffs = (int)(_motionstartPtnOffs - (me.getY() - _motionstartY));
			if (_ptnOffs < 0)
			{
				_ptnOffs = 0;
			}
			
			int maxPtnOffs = (int)(_ptnNotes.length * _noteHeight - this.getHeight());
			if (_ptnOffs > maxPtnOffs)
			{
				_ptnOffs = maxPtnOffs;
			}
		}
		
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
		System.err.println("Measured width is " + MeasureSpec.getSize(widthMeasureSpec));
		
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 
				MeasureSpec.getSize(heightMeasureSpec));
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		System.err.println("onDraw");
		
		
		Paint pObj = new Paint(Paint.ANTI_ALIAS_FLAG);
		pObj.setColor(Color.GREEN);
		canvas.drawCircle(this.getWidth()/2.0f, this.getHeight()/2.0f, this.getWidth()/3.0f, pObj);
		
		
		int displayable = (int)Math.ceil(this.getHeight() / _noteHeight) + 1; // number of displayable notes
		int note = (int)(_ptnOffs / _noteHeight); // idx of first displayable note
		float drawStart = _noteHeight - (_ptnOffs % _noteHeight);
		Log.d(_logTag, "First displayable note is " + note);
		Log.d(_logTag, "ptnOffs is " + _ptnOffs);
		Log.d(_logTag, "drawStart is " + drawStart);
		
		for(int i = 0; i < displayable; i++)
		{
			//Log.d(_logTag, Integer.toString(note + i));
			
			canvas.drawText(Note.nameOf(_ptnNotes[note+i].noteNum), 0.0f, drawStart + (i * _noteHeight), _paintObj);
			//canvas.drawText(Integer.toString(i), this.getWidth()/2.0f, this.getHeight()/2.0f, _paintObj);
		}
		
		//canvas.drawText(Note.nameOf(_note), this.getWidth()/2.0f, this.getHeight()/2.0f, _paintObj);
		
	}

}
