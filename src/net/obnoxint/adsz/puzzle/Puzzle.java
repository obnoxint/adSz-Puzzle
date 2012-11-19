package net.obnoxint.adsz.puzzle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

final class Puzzle {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_IMAGE = "image";
    private static final String PROPERTY_BACKGROUND = "background";
    private static final String PROPERTY_MAX_DIFFICULTY = "maxDifficulty";

    private static Texture getTexture(final Properties properties, final boolean background) {
        Texture r = null;
        try (FileInputStream fis = new FileInputStream(new File(Main.instance.getPuzzleFolder(), ((background) ? properties.getProperty(PROPERTY_BACKGROUND)
                : properties.getProperty(PROPERTY_IMAGE)) + Main.FILE_EXT_PNG))) {
            r = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
        } catch (final IOException e) {
            if (background)
                return null;
            else {
                JOptionPane.showMessageDialog(null, "Unable to load texture for Puzzle \"" + properties.getProperty(PROPERTY_TITLE) + "\": " + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return r;
    }

    private static Properties loadProperties(final String name) {
        final Properties r = new Properties();
        try (FileInputStream fis = new FileInputStream(new File(Main.instance.getPuzzleFolder(), name + Main.FILE_EXT_PROPERTIES))) {
            r.load(fis);
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load properties for \"" + name + "\": " + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Main.writeStackTrace(e);
            System.exit(Main.EXIT_CODE_ERROR);
        }
        return r;
    }

    final String name;
    final String title;
    final Texture texture;
    final Texture background;
    final PuzzleDifficulty maxDifficulty;

    Puzzle(final String name) {
        final Properties p = loadProperties(name);
        this.name = name;
        this.title = p.getProperty(PROPERTY_TITLE);
        this.texture = getTexture(p, false);
        this.background = getTexture(p, true);
        this.maxDifficulty = PuzzleDifficulty.getById(Integer.valueOf(p.getProperty(PROPERTY_MAX_DIFFICULTY)));
    }

}
