#!/usr/bin/env bash

set -e

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do
  DIR="$(cd -P "$( dirname "$SOURCE" )" >/dev/null 2>&1 && pwd)"
  SOURCE="$(readlink "$SOURCE")"
  [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE"
done
SCRIPT_DIR="$(cd -P "$(dirname "$SOURCE")" >/dev/null 2>&1 && pwd)"
ROOT_DIR="${SCRIPT_DIR}/.."
TARGET_DIR="${ROOT_DIR}/target"
DOWNLOAD_DIR="${ROOT_DIR}/download"
TOMCAT_SOURCE="${DOWNLOAD_DIR}/tomcat.tar.gz"
TOMCAT_DOWNLOAD_URL="http://archive.apache.org/dist/tomcat/tomcat-9/v9.0.60/bin/apache-tomcat-9.0.60.tar.gz"
TOMCAT_DIR_NAME="apache-tomcat-9.0.60"
APP_BIN_DIR="${TARGET_DIR}/app/bin"
APP_CONF_DIR="${TARGET_DIR}/app/conf"


mkdir -p "${DOWNLOAD_DIR}"
if [ ! -f "${TOMCAT_SOURCE}" ]; then
  wget -O "${TOMCAT_SOURCE}" "${TOMCAT_DOWNLOAD_URL}"
fi

cd "${ROOT_DIR}" && mvn clean package
tar -xvf "${TOMCAT_SOURCE}" -C "${TARGET_DIR}"
mkdir -p "${APP_BIN_DIR}"
mkdir -p "${APP_CONF_DIR}"
cp -r "${TARGET_DIR}/${TOMCAT_DIR_NAME}/"* "${APP_BIN_DIR}"
rm -rf "${APP_BIN_DIR}/webapps/"*
cp -r "${TARGET_DIR}/monolith.war" "${APP_BIN_DIR}/webapps/ROOT.war"
cp "${ROOT_DIR}/onteon/conf.yml" "${APP_CONF_DIR}"
cp -r "${ROOT_DIR}/tomcat/"* "${APP_BIN_DIR}"
cd "${TARGET_DIR}/app" && tar -zcvf "${TARGET_DIR}/monolith.tar.gz" *