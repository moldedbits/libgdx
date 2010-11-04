
package com.badlogic.gdx.twl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.twl.renderer.GdxRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;

import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 * Convenience class for using TWL. This provides all the basics sufficient for most UIs. TWL can be used without this class if
 * more complex configurations are required (eg, multiple GUI instances).<br>
 * <br>
 * This class provides a single {@link GUI} instance with a root pane set to a widget that takes up the whole screen.
 * {@link #setWidget(Widget)} puts a widget into the root pane, making it easy to layout your widgets using the whole screen.<br>
 * <br>
 * This class is relatively heavyweight because it loads a TWL theme. Generally only one instance should be created for an entire
 * application. Use {@link #setWidget(Widget)} and {@link #clear()} to change the widgets displayed on various application
 * screens.<br>
 * <br>
 * This class implements {@link InputProcessor} and the input methods return true if TWL handled an event. Generally you will want
 * to use {@link InputMultiplexer} to avoid dispatching events that TWL handled to your application.<br>
 * <br>
 * If an instance of this call will no longer be used, {@link #dispose()} must be called to release resources.
 * @author Nathan Sweet <misc@n4te.com>
 */
public class TWL implements InputProcessor {
	private final GdxRenderer renderer = new GdxRenderer();
	private final GUI gui;

	/**
	 * Creates a new TWL instance with the specified theme file. The specified widget is added to the root pane.
	 */
	public TWL (String themeFile, FileType fileType, Widget widget) {
		this(themeFile, fileType);
		setWidget(widget);
	}

	/**
	 * Creates a new TWL instance with the specified theme file.
	 */
	public TWL (String themeFile, FileType fileType) {
		Widget root = new Widget() {
			protected void layout () {
				layoutChildrenFullInnerArea();
			}
		};
		root.setTheme("");

		gui = new GUI(root, renderer, null);
		try {
			gui.applyTheme(ThemeManager.createThemeManager(getThemeURL(themeFile, fileType), renderer));
		} catch (IOException ex) {
			throw new GdxRuntimeException("Error loading theme: " + themeFile + " (" + fileType + ")", ex);
		}
	}

	/**
	 * Returns the GUI instance, which is the root of the TWL UI hierachy and manages timing, inputs, etc.
	 */
	public GUI getGUI () {
		return gui;
	}

	/**
	 * Sets the widget in the GUI's root pane. By default the root pane takes up the whole screen.
	 * @param widget If null, this method is equivalent to {@link #clear()}.
	 */
	public void setWidget (Widget widget) {
		Widget root = gui.getRootPane();
		root.removeAllChildren();
		if (widget != null) root.add(widget);
	}

	/**
	 * Removes all widgets from the GUI's root pane. This effectively means that no TWL UI will be drawn.
	 */
	public void clear () {
		gui.getRootPane().removeAllChildren();
	}

	/**
	 * Draws the TWL UI.
	 */
	public void render () {
		GUI gui = this.gui;
		int viewWidth = Gdx.graphics.getWidth();
		int viewHeight = Gdx.graphics.getHeight();
		if (renderer.getWidth() != viewWidth || renderer.getHeight() != viewHeight) {
			renderer.setSize(viewWidth, viewHeight);
			gui.setSize(viewWidth, viewHeight);
		}
		gui.updateTime();
		gui.handleKeyRepeat();
		gui.handleTooltips();
		gui.updateTimers();
		gui.invokeRunables();
		gui.validateLayout();
		gui.draw();
	}

	public boolean keyDown (int keycode) {
		keycode = getTwlKeyCode(keycode);
		return gui.handleKey(keycode, (char)0, true);
	}

	public boolean keyUp (int keycode) {
		keycode = getTwlKeyCode(keycode);
		return gui.handleKey(keycode, (char)0, false);
	}

	public boolean keyTyped (char character) {
		boolean handled = gui.handleKey(0, character, true);
		return gui.handleKey(0, character, false) || handled;
	}

	public boolean touchDown (int x, int y, int pointer) {
		return gui.handleMouse(x, y, pointer, true);
	}

	public boolean touchUp (int x, int y, int pointer) {
		boolean handled = gui.handleMouse(x, y, pointer, false);
		gui.handleMouse(-9999, -9999, -1, true);
		return handled;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		return gui.handleMouse(x, y, -1, true);
	}

	public void dispose () {
		gui.destroy();
		renderer.dispose();
	}

	static public int getTwlKeyCode (int gdxKeyCode) {
		switch (gdxKeyCode) {
		case Input.Keys.KEYCODE_0: return Event.KEY_0;
		case Input.Keys.KEYCODE_1: return Event.KEY_1;
		case Input.Keys.KEYCODE_2: return Event.KEY_2;
		case Input.Keys.KEYCODE_3: return Event.KEY_3;
		case Input.Keys.KEYCODE_4: return Event.KEY_4;
		case Input.Keys.KEYCODE_5: return Event.KEY_5;
		case Input.Keys.KEYCODE_6: return Event.KEY_6;
		case Input.Keys.KEYCODE_7: return Event.KEY_7;
		case Input.Keys.KEYCODE_8: return Event.KEY_8;
		case Input.Keys.KEYCODE_9: return Event.KEY_9;
		case Input.Keys.KEYCODE_A: return Event.KEY_A;
		case Input.Keys.KEYCODE_B: return Event.KEY_B;
		case Input.Keys.KEYCODE_C: return Event.KEY_C;
		case Input.Keys.KEYCODE_D: return Event.KEY_D;
		case Input.Keys.KEYCODE_E: return Event.KEY_E;
		case Input.Keys.KEYCODE_F: return Event.KEY_F;
		case Input.Keys.KEYCODE_G: return Event.KEY_G;
		case Input.Keys.KEYCODE_H: return Event.KEY_H;
		case Input.Keys.KEYCODE_I: return Event.KEY_I;
		case Input.Keys.KEYCODE_J: return Event.KEY_J;
		case Input.Keys.KEYCODE_K: return Event.KEY_K;
		case Input.Keys.KEYCODE_L: return Event.KEY_L;
		case Input.Keys.KEYCODE_M: return Event.KEY_M;
		case Input.Keys.KEYCODE_N: return Event.KEY_N;
		case Input.Keys.KEYCODE_O: return Event.KEY_O;
		case Input.Keys.KEYCODE_P: return Event.KEY_P;
		case Input.Keys.KEYCODE_Q: return Event.KEY_Q;
		case Input.Keys.KEYCODE_R: return Event.KEY_R;
		case Input.Keys.KEYCODE_S: return Event.KEY_S;
		case Input.Keys.KEYCODE_T: return Event.KEY_T;
		case Input.Keys.KEYCODE_U: return Event.KEY_U;
		case Input.Keys.KEYCODE_V: return Event.KEY_V;
		case Input.Keys.KEYCODE_W: return Event.KEY_W;
		case Input.Keys.KEYCODE_X: return Event.KEY_X;
		case Input.Keys.KEYCODE_Y: return Event.KEY_Y;
		case Input.Keys.KEYCODE_Z: return Event.KEY_Z;
		case Input.Keys.KEYCODE_ALT_LEFT: return Event.KEY_LMETA;
		case Input.Keys.KEYCODE_ALT_RIGHT: return Event.KEY_RMETA;
		case Input.Keys.KEYCODE_BACKSLASH: return Event.KEY_BACKSLASH;
		case Input.Keys.KEYCODE_COMMA: return Event.KEY_COMMA;
		case Input.Keys.KEYCODE_DEL: return Event.KEY_DELETE;
		case Input.Keys.KEYCODE_DPAD_LEFT: return Event.KEY_LEFT;
		case Input.Keys.KEYCODE_DPAD_RIGHT: return Event.KEY_RIGHT;
		case Input.Keys.KEYCODE_DPAD_UP: return Event.KEY_UP;
		case Input.Keys.KEYCODE_DPAD_DOWN: return Event.KEY_DOWN;
		case Input.Keys.KEYCODE_ENTER: return Event.KEY_RETURN;
		case Input.Keys.KEYCODE_HOME: return Event.KEY_HOME;
		case Input.Keys.KEYCODE_MINUS: return Event.KEY_MINUS;
		case Input.Keys.KEYCODE_PERIOD: return Event.KEY_PERIOD;
		case Input.Keys.KEYCODE_PLUS: return Event.KEY_ADD;
		case Input.Keys.KEYCODE_SEMICOLON: return Event.KEY_SEMICOLON;
		case Input.Keys.KEYCODE_SHIFT_LEFT: return Event.KEY_LSHIFT;
		case Input.Keys.KEYCODE_SHIFT_RIGHT: return Event.KEY_RSHIFT;
		case Input.Keys.KEYCODE_SLASH: return Event.KEY_SLASH;
		case Input.Keys.KEYCODE_SPACE: return Event.KEY_SPACE;
		case Input.Keys.KEYCODE_TAB: return Event.KEY_TAB;
		default: return Event.KEY_NONE;
		}
	}

	/**
	 * Returns a URL to a theme file, which can be used with
	 * {@link ThemeManager#createThemeManager(URL, de.matthiasmann.twl.renderer.Renderer) ThemeManager} to create a theme for
	 * {@link GUI#applyTheme(ThemeManager)}. This is only needed if not using the TWL class make use of TWL.
	 */
	static public URL getThemeURL (String themeFile, final FileType fileType) throws MalformedURLException {
		File file = new File(themeFile);
		final File themeRoot = file.getParentFile();
		return new URL("gdx-twl", "local", 80, file.getName(), new URLStreamHandler() {
			protected URLConnection openConnection (URL url) throws IOException {
				final String path = new File(themeRoot, url.getPath()).getPath();
				final FileHandle fileHandle = Gdx.files.getFileHandle(path, fileType);
				return new URLConnection(url) {
					public void connect () {
					}

					public Object getContent () {
						return fileHandle;
					}

					public InputStream getInputStream () {
						if (!path.endsWith(".xml")) return null; // Only theme files are loaded through the URL.
						return fileHandle.readFile();
					}
				};
			}
		});
	}
}
