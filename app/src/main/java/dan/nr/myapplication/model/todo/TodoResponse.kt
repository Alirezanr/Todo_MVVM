package dan.nr.myapplication.model.todo

data class TodoResponse(val message: String,
                        val todos: List<Todo>?)