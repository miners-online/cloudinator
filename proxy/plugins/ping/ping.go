package ping

import (
	"context"
	"os"

	"github.com/go-logr/logr"
	"github.com/miners-online/Cloudinator/proxy/util/mini"
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

		event.Subscribe(p.Event(), 0, onPing(log))

		return nil
	},
}

type MOTD struct {
	description string `yaml:"description"`
}

func onPing(log logr.Logger) func(*proxy.PingEvent) {
	// read the output.yaml file
	data, err := os.ReadFile("ping.yml")

	if err != nil {
		log.Error(err, "Error while reading `ping.yml`")
	}

	// create a person struct and deserialize the data into that struct
	var motd MOTD

	if err := yaml.Unmarshal(data, &motd); err != nil {
		// panic(err)
		motd.description = "Just another Cloudinator proxy! \n No ping config could be found!"
	}

	log.Info(motd.description)

	description := mini.Parse(motd.description)

	log.Info(description.Content)

	return func(e *proxy.PingEvent) {
		p := e.Ping()
		p.Description = description
		p.Players.Max = p.Players.Online + 1
	}
}
