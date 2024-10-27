window.onload = function () {
  //<editor-fold desc="Changeable Configuration Block">

  // the following lines will be replaced by docker/configurator, when it runs in a docker-container
  window.ui = SwaggerUIBundle({
    url: "http://localhost:8080/api/v0/openapi.json",
    dom_id: "#swagger-ui",
    deepLinking: true,
    operationsSorter: function (a, b) {
      var order = { get: "0", post: "1", put: "2", delete: "3" };
      return order[a.get("method")].localeCompare(order[b.get("method")]);
    },
    presets: [SwaggerUIBundle.presets.apis, SwaggerUIStandalonePreset],
    plugins: [SwaggerUIBundle.plugins.DownloadUrl],
    layout: "StandaloneLayout",
  });

  //</editor-fold>
};
