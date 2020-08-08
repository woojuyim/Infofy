package com.wrapper.spotify.exceptions.detailed;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

/**
 * The request could not be understood by the server due to malformed syntax.
 */
public class BadRequestException extends SpotifyWebApiException {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public BadRequestException() {
    super();
  }

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

}
