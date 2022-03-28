#!/usr/bin/env bash

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


mkdir -p "${DOWNLOAD_DIR}"
if [ ! -f "${TOMCAT_SOURCE}" ]; then
  wget -O "${TOMCAT_SOURCE}" "${TOMCAT_DOWNLOAD_URL}"
fi

cd "${ROOT_DIR}" && mvn clean package
tar -xvf "${TOMCAT_SOURCE}" -C "${TARGET_DIR}"
rm -rf "${TARGET_DIR}/${TOMCAT_DIR_NAME}/webapps/"*
cp -rf "${TARGET_DIR}/monolith.war" "${TARGET_DIR}/${TOMCAT_DIR_NAME}/webapps/"