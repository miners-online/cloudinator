FROM --platform=$BUILDPLATFORM golang:1.22.2 AS build

WORKDIR /gate/config
# Copy the Go Modules manifests
COPY go.mod go.sum /workspace/

# cache deps before building and copying source so that we don't need to re-download as much
# and so that source changes don't invalidate our downloaded layer
RUN cd /workspace/ && go mod download

# Copy the go source
COPY plugins /workspace/plugins
COPY util /workspace/util
COPY gate.go /workspace/

# Automatically provided by the buildkit
ARG TARGETOS TARGETARCH

# Build
RUN cd /workspace/ && CGO_ENABLED=0 GOOS=$TARGETOS GOARCH=$TARGETARCH \
    go build -ldflags="-s -w" -a -o gate gate.go

# Move binary into final image
FROM --platform=$BUILDPLATFORM alpine:latest AS app
COPY --from=build /workspace/gate /gate/
#COPY config.yml /
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
