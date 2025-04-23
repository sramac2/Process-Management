package main

import (
	"fmt"
	"log"
	"sync"
	"time"
)

type Task struct {
	ID int
}

type Result struct {
	Message string
}

func processTask(task Task) string {
	fmt.Println("Started processing task ID: ", task.ID)
	time.Sleep(time.Duration(100) * time.Millisecond)
	fmt.Println(task.ID, " Task Completed")
	return fmt.Sprint("Completed task: ", task.ID)
}

func worker(id int, taskChan <-chan Task, resultChan chan<- Result, wg *sync.WaitGroup) {
	defer wg.Done()

	log.Printf("Worker %d started\n", id)

	for task := range taskChan {
		log.Printf("Worker %d picked Task %d\n", id, task.ID)

		output := processTask(task)
		result := Result{
			Message: output,
		}

		resultChan <- result

		log.Printf("Worker %d finished Task %d\n", id, task.ID)
	}

	log.Printf("Worker %d exiting\n", id)
}

func main() {
	numWorkers := 4
	numTasks := 20

	taskChan := make(chan Task, numTasks)
	resultChan := make(chan Result, numTasks)

	var wg sync.WaitGroup

	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go worker(i, taskChan, resultChan, &wg)
	}

	for i := 1; i <= numTasks; i++ {
		taskChan <- Task{ID: i}
	}
	close(taskChan)
	wg.Wait()
	close(resultChan)

	var allResults []Result
	for result := range resultChan {
		allResults = append(allResults, result)
	}

	fmt.Println("\n=== Summary ===")
	for _, r := range allResults {
		fmt.Printf("Task: %s\n", r.Message)
	}
}
