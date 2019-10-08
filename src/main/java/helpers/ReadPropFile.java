package helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropFile {

  public Properties getPropertiesValue(String propertiesFile) {

    InputStream inputStream = null;
    Properties prop = new Properties();
    try {
      inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
      if (inputStream != null) {
        prop.load(inputStream);
      } else {
        throw new FileNotFoundException("property file '" + propertiesFile + "' not found in the classpath");
      }
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return prop;
    }
  }
}