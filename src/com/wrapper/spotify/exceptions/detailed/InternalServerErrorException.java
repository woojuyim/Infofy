package com.wrapper.spotify.exceptions.detailed;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

/**
 * You should never receive this error because our clever coders catch them all ... but if you are unlucky enough to get
 * one, please report it to us.
 */
public class InternalServerErrorException extends SpotifyWebApiException {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public InternalServerErrorException() {
    super();
  }

  public InternalServerErrorException(String message) {
    super(message);
  }

  public InternalServerErrorException(String message, Throwable cause) {
    super(message, cause);
  }

}
