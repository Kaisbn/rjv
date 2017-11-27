SPHINX_FLAGS = -T -E
BUILDDIR = _build
SLIDESDIR = slides

all: all-tex

all-tex:
	sphinx-build -M latex . $(BUILDDIR) $(SPHINX_FLAGS)

all-assistant-tex: BUILDDIR = _build_assistant
all-assistant-tex: SPHINX_FLAGS += -t assistant
all-assistant-tex:
	sphinx-build -M latex . $(BUILDDIR) $(SPHINX_FLAGS)

exercises_list:
	sphinx-build -M exercises_list . $(BUILDDIR) $(SPHINX_FLAGS)

all-pdf: all-tex
	$(MAKE) -C $(BUILDDIR)/latex all-pdf

all-assistant-pdf: BUILDDIR = _build_assistant
all-assistant-pdf: SPHINX_FLAGS += -t assistant
all-assistant-pdf: all-assistant-tex
	$(MAKE) -C $(BUILDDIR)/latex all-pdf

slides:
	$(MAKE) -C $(SLIDESDIR) all

clean:
	rm -rf _build/* _build_assistant/*
	$(MAKE) -C $(SLIDESDIR) clean

.PHONY: slides
