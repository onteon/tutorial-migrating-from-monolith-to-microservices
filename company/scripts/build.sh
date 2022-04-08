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
APP_BIN_DIR="${TARGET_DIR}/app/bin"
APP_CONF_DIR="${TARGET_DIR}/app/conf"

cd "${ROOT_DIR}" && mvn clean package
mkdir -p "${APP_BIN_DIR}"
mkdir -p "${APP_CONF_DIR}"
cp -r "${TARGET_DIR}/company.jar" "${APP_BIN_DIR}"
cp "${ROOT_DIR}/onteon/conf.yml" "${APP_CONF_DIR}"
cd "${TARGET_DIR}/app" && tar -zcvf "${TARGET_DIR}/company.tar.gz" *