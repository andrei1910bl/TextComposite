package com.bulavskiy.handling.reader.impl;

import com.bulavskiy.handling.exception.CustomException;
import com.bulavskiy.handling.reader.CustomFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomFileReaderImpl implements CustomFileReader {

  @Override
  public String readFile(String path) throws CustomException {
    try {
      Path filePath = Path.of(path);
      String contetnt = Files.readString(filePath);
      return contetnt;
    } catch (IOException e) {
      throw new CustomException(e);
    }
  }
}
