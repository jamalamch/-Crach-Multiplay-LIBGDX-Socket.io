package kalima.crach.games.desktop;



import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;

import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;

public class ProgressDialog implements GDXProgressDialog{
	private JOptionPane optionPane;

	private JDialog dialog;

	private CharSequence title = "";
	private CharSequence message = "";

	public ProgressDialog() {
	}

	@Override
	public GDXProgressDialog setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public GDXProgressDialog setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	@Override
	public GDXProgressDialog show() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.debug(GDXDialogsVars.LOG_TAG, ProgressDialog.class.getSimpleName() +
						" now shown.");
				dialog.setVisible(true);
			}

		}).start();
		return this;
	}


	@Override
	public GDXProgressDialog dismiss() {
		dialog.dispose();
		optionPane.setVisible(false);
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, ProgressDialog.class.getSimpleName() + " dismissed.");
		return this;
	}

	@Override
	public GDXProgressDialog build() { 
		if(dialog == null) {
		optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
				new Object[] {}, null);
		dialog = new JDialog();

		dialog.setTitle((String) title);
		dialog.setModal(true);
		dialog.setFocusable(true);
		dialog.setAlwaysOnTop(true);
        dialog.setLocation(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		dialog.setContentPane(optionPane);
		dialog.pack();
		}
		return this;
	}

}
