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

"${ROOT_DIR}/company/scripts/build.sh"
"${ROOT_DIR}/product/scripts/build.sh"
"${ROOT_DIR}/report/scripts/build.sh"
"${ROOT_DIR}/gateway/scripts/build.sh"
"${ROOT_DIR}/frontend/scripts/build.sh"
