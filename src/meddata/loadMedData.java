/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meddata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lk0
 */
public class loadMedData {

    public static MedData get() {
        File file = new File(System.getProperty("user.dir") + "\\data.med");
        if (file.exists()) {
            try {
                FileInputStream in = new FileInputStream(System.getProperty("user.dir")
                        + "\\data.med");
                ObjectInputStream objIn = new ObjectInputStream(in);
                MedData md = (MedData) objIn.readObject();
                in.close();
                objIn.close();
                return md;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        } else {
            MedData md = new MedData();
            save(md);
            return md;
        }
    }

    public static boolean save(MedData data) {
        try {
            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")
                    + "\\data.med");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(data);
            objOut.flush();
            out.close();
            objOut.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
}
