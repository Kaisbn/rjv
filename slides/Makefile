TARGET  = slides.pdf
THEME   = yaka2019
PROJECT = ../project.yml

RM           ?= rm -f
BUTLERFLAGS   = --project=${PROJECT} --theme=${THEME}

all: $(TARGET)

clean:
	${RM} ${TARGET}
	$(RM) *.out *.aux *.log

%.pdf: %.rst
	butler-slides ${BUTLERFLAGS} $< -o $@

.PHONY: all clean
