package main_files;
import main_files.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import javafx.scene.paint.Color;

public class Settings
{
	public static void main(String[] args) {
		Color[] orbColors = new Color[8];
		orbColors[0] = Color.rgb(216, 221, 115);
		orbColors[1] = Color.rgb(32, 86, 173);
		orbColors[2] = Color.rgb(31, 130, 42);
		orbColors[3] = Color.rgb(186, 26, 14);
		orbColors[4] = Color.rgb(150, 121, 16);
		orbColors[5] = Color.rgb(16, 129, 150);
		orbColors[6] = Color.rgb(96, 16, 150);
		orbColors[7] = Color.rgb(150, 16, 94);

		RenderGUISettings x = new RenderGUISettings();
		x.render();
	}
}