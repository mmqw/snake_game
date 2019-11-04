package model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class Sound {

    Media bkMusic;
    MediaPlayer bkAudio;

    private AudioClip biteSoundFX;
    private AudioClip levelUpFX;
    private AudioClip endGameFx;

    public Sound() throws URISyntaxException {
        levelUpFX =
                new AudioClip(getClass().getResource("/Resources/LevelUp.mp3").toURI().toString());
        levelUpFX.setRate(3);
        bkMusic = new Media(getClass().getResource("/Resources/BackgroundMusic.mp3").toURI().toString());
        // initializing media player with the music
        bkAudio = new MediaPlayer(bkMusic);biteSoundFX = new AudioClip(getClass().getResource("/Resources/Bite.mp3").toURI().toString());
        biteSoundFX.setRate(3);
        endGameFx = new AudioClip(getClass().getResource("/Resources/EndGame.mp3").toURI().toString());
        endGameFx.setVolume(0.8);
    }

    public MediaPlayer getBackgroundAudio() {
        return bkAudio;
    }
    public AudioClip getBiteSound() {
        return biteSoundFX;
    }
    public AudioClip getLevelUpSound() {
        return levelUpFX;
    }
    public AudioClip getEndGameSound() {
        return endGameFx;
    }
}