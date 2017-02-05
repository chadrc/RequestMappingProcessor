package main

import(
	"net/http"
)

func main() {
	http.HandleFunc("/", handlerGenerator(RootHandler{}))
	http.ListenAndServe(":8000", nil)
}

func handlerGenerator(handler RestHandler) func(writer http.ResponseWriter, request *http.Request) {
	return func(writer http.ResponseWriter, request *http.Request) {
		var handled bool
		switch request.Method {
		case "GET":
			handled = handler.handleGet(writer, request)
		case "POST":
			handled = handler.handlePost(writer, request)
		case "PUT":
			handled = handler.handlePut(writer, request)
		case "DELETE":
			handled = handler.handleDelete(writer, request)
		case "OPTIONS":
			handled = handler.handleOptions(writer, request)
		default:
			writer.WriteHeader(http.StatusMethodNotAllowed)
		}

		if !handled {
			writer.WriteHeader(http.StatusMethodNotAllowed)
		}
	}
}

type RestHandler interface {
	// Functions return true if the method is supported and false otherwise
	handleGet(writer http.ResponseWriter, request *http.Request) bool
	handlePost(writer http.ResponseWriter, request *http.Request) bool
	handlePut(writer http.ResponseWriter, request *http.Request) bool
	handleDelete(writer http.ResponseWriter, request *http.Request) bool
	handleOptions(writer http.ResponseWriter, request *http.Request) bool
}

type RootHandler struct {

}

func (RootHandler) handleGet(writer http.ResponseWriter, request *http.Request) bool {
	writer.Write([]byte("{status: 'OK'}"))
	return true
}

func (RootHandler) handlePost(writer http.ResponseWriter, request *http.Request) bool {
	writer.Write([]byte("{status: 'OK'}"))
	return true
}

func (RootHandler) handlePut(writer http.ResponseWriter, request *http.Request) bool {
	writer.Write([]byte("{status: 'OK'}"))
	return true
}

func (RootHandler) handleDelete(writer http.ResponseWriter, request *http.Request) bool {
	writer.Write([]byte("{status: 'OK'}"))
	return true
}

func (RootHandler) handleOptions(writer http.ResponseWriter, request *http.Request) bool {
	writer.Write([]byte("{status: 'OK'}"))
	return true
}
