package ping

import (
	"context"
	"os"

	"github.com/go-logr/logr"
	. "github.com/miners-online/Cloudinator/proxy/util"
	"github.com/robinbraemer/event"
	"go.minekube.com/gate/pkg/edition/java/proxy"
	"gopkg.in/yaml.v3"
)

// Plugin is a ping plugin that handles ping events.
var Plugin = proxy.Plugin{
	Name: "Ping",
	Init: func(ctx context.Context, p *proxy.Proxy) error {
		log := logr.FromContextOrDiscard(ctx)
		log.Info("Hello from Ping plugin!")

		event.Subscribe(p.Event(), 0, onPing(&log))

		return nil
	},
}

type MOTD struct {
	line1 string `yaml:"line1"`
	line2 string `yaml:"line2"`
}

func onPing(log *logr.Logger) func(*proxy.PingEvent) {
	data, err := os.ReadFile("ping.yml")

	log.Info("", "data", data)

	if err != nil {
		log.Error(err, "Error while reading `ping.yml`")
	}

	var motd MOTD

	err = yaml.Unmarshal(data, &motd)
	if err != nil {
		log.Error(err, "Error while reading `ping.yml`")
		motd.line1 = "&b&lJust another Cloudinator proxy!&r"
		motd.line2 = "&cNo ping config could be found!"
	}

	log.Info("", "motd", motd)

	if motd.line1 == "" && motd.line2 == "" {
		motd.line1 = "&b&lJust another Cloudinator proxy!&r"
		motd.line2 = "&cNo ping config could be found!"
	}

	return func(e *proxy.PingEvent) {
		p := e.Ping()
		p.Description = Join(Text(motd.line1 + "\n" + motd.line2))
		p.Players.Max = p.Players.Online + 1
	}
}
