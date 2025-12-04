package com.bulavskiy.handling.reader;

import com.bulavskiy.handling.exception.CustomException;

public interface CustomFileReader {
  String readFile(String path) throws CustomException;
}
