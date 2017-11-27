/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.languageserver;

import org.eclipse.che.api.languageserver.exception.LanguageServerException;
import org.eclipse.che.api.languageserver.shared.util.Constants;
import org.eclipse.che.api.vfs.Path;
import org.eclipse.lsp4j.Location;

/**
 * Language service service utilities
 *
 * @author Thomas Mäder
 * @author Vitalii Parfonov
 * @author Yevhen Vydolob
 * @author Dmytro Kulieshov
 */
public class LanguageServiceUtils {

  private static final String PROJECTS = "/projects";
  private static final String FILE_PROJECTS = "file:///projects";

  public static String prefixURI(String relativePath) {
    return FILE_PROJECTS + relativePath;
  }

  public static String removePrefixUri(String uri) {
    return uri.startsWith(FILE_PROJECTS) ? uri.substring(FILE_PROJECTS.length()) : uri;
  }

  public static String removeUriScheme(String uri) {
    return uri.startsWith(FILE_PROJECTS) ? uri.substring("file://".length()) : uri;
  }

  public static boolean truish(Boolean b) {
    return b != null && b;
  }

  public static boolean isProjectUri(String path) {
    return path.startsWith(FILE_PROJECTS);
  }

  public static boolean isStartWithProject(String path) {
    return path.startsWith(PROJECTS);
  }

  public static String prefixProject(String path) {
    return path.startsWith(PROJECTS) ? path : PROJECTS + path;
  }

  public static String extractProjectPath(String fileUri) throws LanguageServerException {
    if (!isProjectUri(fileUri)) {
      throw new LanguageServerException("Not a project URI: " + fileUri);
    }
    Path path = Path.of(removeUriScheme(fileUri));
    if (path.length() < 1) {
      throw new LanguageServerException("Not a project URI: " + fileUri);
    }
    return path.subPath(0, 2).toString();
  }

  public static Location fixLocation(Location o) {
    if (isProjectUri(o.getUri())) {
      o.setUri(Constants.CHE_WKSP_SCHEME + removePrefixUri(o.getUri()));
    }
    return o;
  }
}