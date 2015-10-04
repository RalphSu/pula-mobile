/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.pula.star.datepicker.wheelview.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Numeric Wheel adapter.
 */
public class ListWheelAdapter extends AbstractWheelTextAdapter {

	/** The default min value */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value */
	private static final int DEFAULT_MIN_VALUE = 0;

	// Values
	private int minValue;
	private int maxValue;

	// format
	private String format;

	private String label;

	private ArrayList item_list= new ArrayList();

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the current context
	 * @param list
	 *            the wheel string list
	 */
	public ListWheelAdapter(Context context, String list[]) {
		super(context);
		for (int i = 0; i < list.length; i++) {
			this.item_list.add(list[i]);
		}
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < item_list.size()) {
			//int value = minValue + index;
			//return format != null ? String.format(format, value) : Integer.toString(value);
			return item_list.get(index).toString();
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return item_list.size();
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		if (index >= 0 && index < getItemsCount()) {
			if (convertView == null) {
				convertView = getView(itemResourceId, parent);
			}
			TextView textView = getTextView(convertView, itemTextResourceId);
			if (textView != null) {
				CharSequence text = getItemText(index);
				if (text == null) {
					text = "";
				}
				textView.setText(text + label);

				if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
					configureTextView(textView);
				}
			}
			return convertView;
		}
		return null;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
