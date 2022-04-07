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

for i in {1..10}; do onteoncli application-registry upload "${ROOT_DIR}/company/target/company.tar.gz" && break || sleep 2; done
for i in {1..10}; do onteoncli application-registry upload "${ROOT_DIR}/product/target/product.tar.gz" && break || sleep 2; done
for i in {1..10}; do onteoncli application-registry upload "${ROOT_DIR}/report/target/report.tar.gz" && break || sleep 2; done
for i in {1..10}; do onteoncli application-registry upload "${ROOT_DIR}/gateway/target/gateway.tar.gz" && break || sleep 2; done
for i in {1..10}; do onteoncli application-registry upload "${ROOT_DIR}/frontend/target/frontend.tar.gz" && break || sleep 2; done
