package com.wrapper.spotify.exceptions.detailed;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

/**
 * The server understood the request, but is refusing to fulfill it.
 */
public class ForbiddenException extends SpotifyWebApiException {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public ForbiddenException() {
    super();
  }

  public ForbiddenException(String message) {
    super(message);
  }

  public ForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }

}
