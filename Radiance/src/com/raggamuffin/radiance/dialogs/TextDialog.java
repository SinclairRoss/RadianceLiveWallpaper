// Author - Sinclair Ross.
// Date - 12/04/2014

// A simple text dialog that only has one function,
// to display a message from myself to the user.

package com.raggamuffin.radiance.dialogs;

import com.raggamuffin.radiance.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class TextDialog extends DialogPreference
{
	public TextDialog(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		setDialogLayoutResource(R.layout.textdialog_layout);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder)
	{
		super.onPrepareDialogBuilder(builder);
		builder.setNegativeButton(null, null);	
	}
}
