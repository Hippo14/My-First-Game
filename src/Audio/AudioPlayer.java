/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author ASUS
 */
public class AudioPlayer {
    
    private Clip clip;
     
    public AudioPlayer(String s) {
        try {
            AudioInputStream audio_stream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
            AudioFormat base_format = audio_stream.getFormat();
            AudioFormat decode_format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, base_format.getSampleRate(),16, base_format.getChannels(), base_format.getChannels() * 2, base_format.getSampleRate(), false
            );
            AudioInputStream decoded_audio_stream = AudioSystem.getAudioInputStream(decode_format, audio_stream);
            clip = AudioSystem.getClip();
            clip.open(decoded_audio_stream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
        if (clip == null) 
            return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void stop() {
        if (clip.isRunning())
            clip.stop();
    }
    
    public void close() {
        stop();
        clip.close();
    }
}
