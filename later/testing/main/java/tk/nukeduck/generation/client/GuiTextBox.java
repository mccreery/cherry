package tk.nukeduck.generation.client;

import java.awt.event.KeyEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;

public class GuiTextBox extends Gui {
	/** The X coordinate of this textbox. */
	private int x;
	/** The X coordinate of this textbox. */
	private int y;
	/** The X coordinate of the right of this textbox. */
	private int x2;
	/** The Y coordinate of the bottom of this textbox. */
	private int y2;

	private FontRenderer fr;

	public boolean alwayshasFocus = false;
	public boolean hasFocus = false;
	private int selectStart = 0, selectEnd = 0;

	private StringBuilder text;
	private int maxLength;

	public GuiTextBox(FontRenderer fr, int x, int y, int width, int height, int maxLength) {
		this.fr = Minecraft.getMinecraft().fontRenderer;
		this.x = x;
		this.y = y;
		this.x2 = x + width;
		this.y2 = y + height;

		this.maxLength = maxLength;
		this.text = new StringBuilder(this.maxLength);
	}

	public CharSequence getText() {
		return this.text;
	}

	public GuiTextBox setX(int x) {
		int d = x - this.x;
		this.x = x;
		this.x2 += d;
		return this;
	}
	public int getX() {
		return this.x;
	}

	public GuiTextBox setY(int y) {
		int d = y - this.y;
		this.y = y;
		this.y2 += d;
		return this;
	}
	public int getY() {
		return this.y;
	}

	public GuiTextBox setWidth(int width) {
		this.x2 = this.x + width;
		return this;
	}
	public int getWidth() {
		return this.x2 - this.x;
	}

	public GuiTextBox setHeight(int height) {
		this.y2 = this.y + height;
		return this;
	}
	public int getHeight() {
		return this.y2 - this.y;
	}

	public int cursorTicks = 0;
	private boolean renderCursor = true;

	public void cursorTick() {
		cursorTicks++;
		this.renderCursor = (cursorTicks %= 20) < 10;
	}

	public boolean keyTyped(char c, int i) {
		if(!this.hasFocus) return false;

		switch(c) {
			case 1: // Ctrl + A
				this.select(0, this.text.length());
				return true;
			case 3:
				GuiScreen.setClipboardString(this.getSelection());
				return true;
			case 22:
				this.write(GuiScreen.getClipboardString());
				return true;
			case 24:
				GuiScreen.setClipboardString(this.getSelection());
				this.deleteSelection();
				return true;
			default:
				switch(i) {
					case 14: // Backspace
						if(this.hasSelection()) {
							this.deleteSelection();
						} else if(this.selectStart > 0) {
							if(GuiScreen.isCtrlKeyDown()) {
								int last = this.lastWord(this.selectStart);
								this.delete(last, this.selectStart);
								this.moveCursor(last);
							} else {
								this.moveCursor(this.selectStart - 1);
								this.delete(this.selectStart);
							}
						}
						return true;
					case 199: // Home
						if(GuiScreen.isShiftKeyDown()) {
							this.selectEnd = 0;
						} else {
							this.moveCursor(0);
						}
						return true;
					case 203: // Left
						if(GuiScreen.isShiftKeyDown()) {
							if(GuiScreen.isCtrlKeyDown()) {
								this.selectEnd = this.lastWord(this.selectEnd);
							} else {
								this.selectEnd--;
								this.assertSelection();
							}
						} else if(this.hasSelection()) {
							if(GuiScreen.isCtrlKeyDown()) {
								if(this.selectStart > this.selectEnd) {
									this.moveCursor(this.lastWord(this.selectEnd));
								} else {
									this.moveCursor(this.lastWord(this.selectStart));
								}
							} else {
								if(this.selectStart > this.selectEnd) {
									this.moveCursor(this.selectEnd);
								} else {
									this.moveCursor(this.selectStart);
								}
							}
						} else {
							if(GuiScreen.isCtrlKeyDown()) {
								this.moveCursor(this.lastWord(this.selectStart));
							} else {
								this.moveCursor(this.selectStart - 1);
							}
						}
						return true;
					case 205: // Right
						if(GuiScreen.isShiftKeyDown()) {
							if(GuiScreen.isCtrlKeyDown()) {
								this.selectEnd = this.nextWord(this.selectEnd);
							} else {
								this.selectEnd++;
								this.assertSelection();
							}
						} else if(this.hasSelection()) {
							if(GuiScreen.isCtrlKeyDown()) {
								if(this.selectStart > this.selectEnd) {
									this.moveCursor(this.nextWord(this.selectEnd));
								} else {
									this.moveCursor(this.nextWord(this.selectStart));
								}
							} else {
								if(this.selectStart > this.selectEnd) {
									this.moveCursor(this.selectStart);
								} else {
									this.moveCursor(this.selectEnd);
								}
							}
						} else {
							if(GuiScreen.isCtrlKeyDown()) {
								this.moveCursor(this.nextWord(this.selectStart));
							} else {
								this.moveCursor(this.selectStart + 1);
							}
						}
						return true;
					case 207: // End
						if(GuiScreen.isShiftKeyDown()) {
							this.selectEnd = this.text.length();
						} else {
							this.moveCursor(this.text.length());
						}
						return true;
					case 211: // Delete
						if(GuiScreen.isCtrlKeyDown() && GuiScreen.isShiftKeyDown()) {
							this.delete(this.selectEnd, this.text.length());
							this.moveCursor(this.selectEnd);
						} else {
							if(this.hasSelection()) {
								this.deleteSelection();
							} else if(!GuiScreen.isShiftKeyDown()) {
								if(GuiScreen.isCtrlKeyDown()) {
									this.delete(this.selectStart, this.nextWord(this.selectStart));
								} else {
									this.delete(this.selectStart);
								}
							}
						}
						return true;
					default:
						if(ChatAllowedCharacters.isAllowedCharacter(c)) {
							this.write(c);
							return true;
						}
						return false;
			}
		}
	}

	public void mouseClicked(int x, int y, int button) {
		this.hasFocus = this.alwayshasFocus || (x >= this.x && x < this.x2 && y >= this.y && y < this.y2);
		if(button != 0) return;

		if(this.hasFocus) this.moveCursor(this.fr.trimStringToWidth(this.text.toString(), x - this.x - 5).length());
	}

	public GuiTextBox clearSelection() {
		return moveCursor(0);
	}
	public GuiTextBox moveCursorBy(int x) {
		return moveCursor(this.selectStart + x);
	}
	public GuiTextBox moveCursor(int i) {
		return select(i, i);
	}
	public GuiTextBox select(int start, int end) {
		this.selectStart = start;
		this.selectEnd = end;
		return this.assertSelection();
	}

	public GuiTextBox assertSelection() {
		if(this.selectStart < 0) {
			this.selectStart = 0;
		} else if(this.selectStart > this.text.length()) {
			this.selectStart = this.text.length();
		}
		if(this.selectEnd < 0) {
			this.selectEnd = 0;
		} else if(this.selectEnd > this.text.length()) {
			this.selectEnd = this.text.length();
		}
		return this;
	}

	public boolean hasSelection() {
		return this.selectStart != this.selectEnd;
	}
	public int getSelectStart() {
		return this.selectStart;
	}
	public int getSelectEnd() {
		return this.selectEnd;
	}
	public String getSelection() {
		if(this.selectStart > this.selectEnd) {
			return this.text.substring(this.selectEnd, this.selectStart);
		} else {
			return this.text.substring(this.selectStart, this.selectEnd);
		}
	}

	public GuiTextBox clear() {
		this.text.setLength(0);
		return this;
	}

	public GuiTextBox setText(CharSequence text) {
		this.text.setLength(0);
		return this.append(text);
	}

	public GuiTextBox append(int x) {
		return this.append(String.valueOf(x));
	}
	public GuiTextBox append(char c) {
		if(this.text.length() < this.maxLength) {
			this.text.append(c);
			this.moveCursor(this.text.length());
		}
		return this;
	}
	public GuiTextBox append(CharSequence text) {
		if(this.text.length() + text.length() > this.maxLength) {
			this.text.append(text, 0, this.maxLength - text.length());
		} else {
			this.text.append(text);
		}
		this.moveCursor(this.text.length());
		return this;
	}

	public GuiTextBox insert(int i, int x) {
		return this.insert(i, String.valueOf(x));
	}
	public GuiTextBox insert(int i, char c) {
		if(this.text.length() < this.maxLength) {
			this.text.insert(i, c);
			this.moveCursor(i + 1);
		}
		return this;
	}
	public GuiTextBox insert(int i, CharSequence text) {
		if(this.text.length() + text.length() > this.maxLength) {
			int length = this.maxLength - this.text.length();
			this.text.insert(i, text, 0, length);
			this.moveCursor(i + length);
		} else {
			this.text.insert(i, text);
			this.moveCursor(i + text.length());
		}
		return this;
	}

	public GuiTextBox delete(int i) {
		if(i <= this.text.length()) this.text.deleteCharAt(i);
		return this;
	}
	public GuiTextBox delete(int start, int end) {
		if(start >= 0 && end <= this.text.length()) this.text.delete(start, end);
		return this;
	}

	public GuiTextBox deleteSelection() {
		if(this.hasSelection()) {
			if(this.selectStart > this.selectEnd) {
				this.delete(this.selectEnd, this.selectStart);
				this.moveCursor(this.selectEnd);
			} else {
				this.delete(this.selectStart, this.selectEnd);
				this.moveCursor(this.selectStart);
			}
		}
		return this;
	}

	public GuiTextBox write(int x) {
		return this.write(String.valueOf(x));
	}
	public GuiTextBox write(char c) {
		this.deleteSelection();
		return this.insert(this.selectStart, c);
	}
	public GuiTextBox write(CharSequence text) {
		this.deleteSelection();
		return this.insert(this.selectStart, text);
	}

	public int nextWord(int i) {
		i--;
		while(++i != this.text.length() && !Character.isWhitespace(this.text.charAt(i)));
		i--;
		while(++i != this.text.length() && Character.isWhitespace(this.text.charAt(i)));
		return i;
	}
	public int lastWord(int i) {
		while(--i != -1 && Character.isWhitespace(this.text.charAt(i)));
		i++;
		while(--i != -1 && !Character.isWhitespace(this.text.charAt(i)));
		i++;
		return i;
	}

	public int getLength() {
		return this.text.length();
	}

	public void draw() {
		/*if(this.hasFocus) {
			this.drawRect(x - 1, y - 1, x2 + 1, y2 + 1, 0x442C75FF);
		}
		this.drawRect(x, y, x2, y2, 0xFF222222);
		this.drawRect(x, y, x2, y + 1, 0xFF666666);*/
		int h = this.fr.FONT_HEIGHT;
		this.fr.drawString(this.text.toString(), x + 2, y + (this.getHeight() - h) / 2, 0x00000000);

		//this.drawString(this.fr, this.selectStart + ", " + this.selectEnd, x, y2 + 5, 0xFFFFFFFF);
		if(this.hasSelection() || (this.renderCursor && this.hasFocus)) {
			int a = x + 1 + this.fr.getStringWidth(this.text.toString().substring(0, this.selectStart));
			int b = x + 1 + this.fr.getStringWidth(this.text.toString().substring(0, this.selectEnd));

			if(a > b) {
				this.drawRect(a, y + (this.getHeight() - h) / 2, b + 1, y + (this.getHeight() + h) / 2, 0x44000000/*0x66FFFFFF*/);
			} else {
				this.drawRect(b, y + (this.getHeight() - h) / 2, a + 1, y + (this.getHeight() + h) / 2, 0x44000000/*0x66FFFFFF*/);
			}
		}
	}
}
