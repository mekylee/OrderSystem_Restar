package com.example.ordersystem_rest.customview;

import java.util.jar.Attributes;



import com.example.ordersytem_rest.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class CleanableEditText extends EditText {
	private Context eContext;
	private TextWatcherCallBack eCallBack; // �ص�����
	private Drawable eDrawable; // �Ҳ�ɾ��ͼ��

	// �ص��ӿ�
	public interface TextWatcherCallBack {
		public void handleMoreTextChanged();
	}

	public CleanableEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.eContext = context;
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.eContext = context;
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.eContext = context;
		init();
	}

	public void init() {
		eDrawable = eContext.getResources().getDrawable(R.drawable.clear);
		eCallBack = null;
		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				// ����״̬������ǹ���ʾɾ��ť
				updateCleanable(length(), true);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				// ����״̬,����Ƿ���ʾɾ��ť
				updateCleanable(length(), true);
				// �������activity�����ûص�����˴����Դ���
				if (eCallBack != null) {
					eCallBack.handleMoreTextChanged();
				}
			}
		};
		this.addTextChangedListener(textWatcher);
		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				// ����״̬,����Ƿ���ʾɾ��ť
				updateCleanable(length(), arg1);
			}
		});
	}

	// �����ݲ�Ϊ���һ�ý���ʱ������ʾ�Ҳ�ɾ��ť
	public void updateCleanable(int length, boolean hasFocus) {
		if (length() > 0 && hasFocus) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, eDrawable, null);
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		final int DAWABLE_RIGHT = 2;
		// ���Ի�����������ĸ�drawable,�Ҳ�ڶ���ͼ��û������Ϊ��
		Drawable rightIcon = getCompoundDrawables()[DAWABLE_RIGHT];
		if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
			// �������λ���Ƿ����Ҳ��ɾ��ͼ��
			// ע�⣬ʹ��getRightX()�ǻ�ȡ��Ļ���λ�ã�getX()���ܻ�ȡ��Ը������λ��
			int leftEdgeOfRightDrawable = getRight() - getPaddingRight()
					- rightIcon.getBounds().width();
			if (event.getRawX() >= leftEdgeOfRightDrawable) {
				setText("");
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		eDrawable = null;
		super.finalize();
	}

}
