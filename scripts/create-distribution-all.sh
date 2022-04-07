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

onteoncli distribution create-from-file "${ROOT_DIR}/company/onteon/distribution.yml"
onteoncli distribution create-from-file "${ROOT_DIR}/product/onteon/distribution.yml"
onteoncli distribution create-from-file "${ROOT_DIR}/report/onteon/distribution.yml"
onteoncli distribution create-from-file "${ROOT_DIR}/gateway/onteon/distribution.yml"
onteoncli distribution create-from-file "${ROOT_DIR}/frontend/onteon/distribution.yml"
